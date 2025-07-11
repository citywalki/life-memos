package pro.walkin.memos.model.user

import pro.walkin.memos.model.TableId

data class UserSetting(
    val id: TableId,
    var locale: Locale = Locale.CN,
    var appearance: String = "system",
) {
    enum class Locale {
        EN,
        CN,
    }
}
