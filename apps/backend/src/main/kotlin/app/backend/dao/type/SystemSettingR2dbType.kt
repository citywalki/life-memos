package app.backend.dao.type

import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import kotlinx.serialization.json.Json
import model.SystemSettingDetail
import org.komapper.r2dbc.spi.R2dbcUserDefinedDataType
import kotlin.reflect.typeOf

class SystemSettingR2dbType : R2dbcUserDefinedDataType<SystemSettingDetail> {
    val jsonUtil = Json.Default
    override val name: String
        get() = "jsonb"
    override val type = typeOf<SystemSettingDetail>()

    override val r2dbcType: Class<io.r2dbc.postgresql.codec.Json> = io.r2dbc.postgresql.codec.Json::class.javaObjectType

    override fun getValue(
        row: Row,
        index: Int,
    ): SystemSettingDetail? = getValue(row.get(index))

    override fun getValue(
        row: Row,
        columnLabel: String,
    ): SystemSettingDetail? = getValue(row.get(columnLabel))

    fun getValue(value: Any?): SystemSettingDetail? =
        value?.let {
            if (value is io.r2dbc.postgresql.codec.Json) {
                jsonUtil.decodeFromString<SystemSettingDetail>(value.asString())
            } else {
                null
            }
        }

    override fun setValue(
        statement: Statement,
        index: Int,
        value: SystemSettingDetail,
    ) {
        statement.bind(index, getValue(value))
    }

    override fun setValue(
        statement: Statement,
        name: String,
        value: SystemSettingDetail,
    ) {
        statement.bind(name, getValue(value))
    }

    private fun getValue(value: SystemSettingDetail): io.r2dbc.postgresql.codec.Json =
        io.r2dbc.postgresql.codec.Json
            .of(jsonUtil.encodeToString(value))

    override fun toString(value: SystemSettingDetail): String = jsonUtil.encodeToString(value)
}
