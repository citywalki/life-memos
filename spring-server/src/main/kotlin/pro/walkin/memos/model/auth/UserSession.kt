package pro.walkin.memos.model.auth

import pro.walkin.memos.model.user.UserId
import java.time.LocalDateTime

data class UserSession(
    val userId: UserId,
    val expirationTime: LocalDateTime,
) {
    companion object {
        fun create(userId: UserId): UserSession = UserSession(userId, LocalDateTime.now().plusDays(5))
    }
}
