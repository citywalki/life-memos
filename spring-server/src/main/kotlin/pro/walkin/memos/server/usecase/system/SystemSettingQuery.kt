package pro.walkin.memos.server.usecase.system

import org.komapper.core.dsl.Meta
import org.komapper.core.dsl.QueryDsl
import org.komapper.core.dsl.query.map
import org.komapper.core.dsl.query.singleOrNull
import org.komapper.r2dbc.R2dbcDatabase
import org.springframework.stereotype.Service
import pro.walkin.memos.model.GeneralSystemSettingDetail
import pro.walkin.memos.model.SystemSettingKey
import pro.walkin.memos.server.usecase.system.entity.systemSetting

@Service
class SystemSettingQuery(
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
