package model.auth

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import model.user.UserId
import kotlin.time.Duration.Companion.days

@Serializable
data class UserSession(val userId: UserId, val expirationTime: Instant) {
    companion object {
        fun create(userId: UserId): UserSession {
            return UserSession(userId, Clock.System.now().plus(5.days))
        }
    }
}
