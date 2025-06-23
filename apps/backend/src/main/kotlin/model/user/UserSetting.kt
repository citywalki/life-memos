package model.user

import kotlinx.serialization.Serializable
import model.TableId

@Serializable
data class UserSetting(
    val id: TableId,
    var locale: Locale = Locale.CN,
    var appearance: String = "system",
) {
    @Serializable
    enum class Locale {
        EN,
        CN,
    }
}
