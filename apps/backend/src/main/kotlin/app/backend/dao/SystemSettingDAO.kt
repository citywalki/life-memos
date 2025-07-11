package app.backend.dao

import app.backend.dao.def.systemSetting
import model.GeneralSystemSettingDetail
import model.SystemSettingKey
import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase

class SystemSettingDAO(
    private val db: R2dbcDatabase,
) {
    private val ss = Meta.systemSetting

    suspend fun findGeneralSystemSetting(): GeneralSystemSettingDetail {
        val query =
            QueryDsl.Companion
                .from(ss)
                .where {
                    ss.key eq SystemSettingKey.GENERAL
                }.singleOrNull()
                .map {
                    it?.let {
                        it.value as GeneralSystemSettingDetail
                    } ?: GeneralSystemSettingDetail()
                }

        return db.runQuery(query)
    }
}
