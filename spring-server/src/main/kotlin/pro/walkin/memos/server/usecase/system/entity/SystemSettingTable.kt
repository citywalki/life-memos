package pro.walkin.memos.server.usecase.system.entity

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import pro.walkin.memos.model.SystemSettingDetail
import pro.walkin.memos.model.SystemSettingKey

@KomapperEntity(aliases = ["systemSetting"])
@KomapperTable("system_setting")
data class SystemSettingTable(
    @KomapperId val key: SystemSettingKey,
    val value: SystemSettingDetail,
)
