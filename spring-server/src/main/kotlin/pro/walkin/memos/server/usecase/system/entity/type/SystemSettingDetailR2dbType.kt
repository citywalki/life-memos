package pro.walkin.memos.server.usecase.system.entity.type

import com.fasterxml.jackson.module.kotlin.readValue
import io.r2dbc.spi.Row
import io.r2dbc.spi.Statement
import org.komapper.r2dbc.spi.R2dbcUserDefinedDataType
import pro.walkin.memos.model.SystemSettingDetail
import pro.walkin.memos.server.common.JSON
import kotlin.jvm.javaObjectType
import kotlin.let
import kotlin.reflect.typeOf

class SystemSettingDetailR2dbType : R2dbcUserDefinedDataType<SystemSettingDetail> {
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
                JSON.readValue<SystemSettingDetail>(value.asString())
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
            .of(JSON.writeValueAsString(value))

    override fun toString(value: SystemSettingDetail): String = JSON.writeValueAsString(value)
}
