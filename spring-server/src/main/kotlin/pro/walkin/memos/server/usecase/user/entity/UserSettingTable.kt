package pro.walkin.memos.server.usecase.user.entity

import org.komapper.annotation.KomapperEntity
import org.komapper.annotation.KomapperId
import org.komapper.annotation.KomapperTable
import pro.walkin.memos.model.user.UserId

@KomapperEntity(aliases = ["userSetting"])
@KomapperTable("user_setting")
data class UserSettingTable(
    @KomapperId val userId: UserId,
    val key: UserSettingKey,
    val value: String,
) {
    enum class UserSettingKey {
        USER_SETTING_KEY_UNSPECIFIED,

        // Access tokens for the user.
        ACCESS_TOKENS,

        // The locale of the user.
        LOCALE,

        // The appearance of the user.
        APPEARANCE,

        // The visibility of the memo.
        MEMO_VISIBILITY,

        // The shortcuts of the user.
        SHORTCUTS,
    }
}
