package model.user

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import shared.infra.IdWorker

@Serializable
data class UserFollowChannel(
    val uid: UserFollowChannelId,
    val userId: UserId,
    val channelId: UserChannelId,
    val channelOwner: UserId,
    val followStartAt: LocalDateTime,
)

@Serializable
@JvmInline
value class UserFollowChannelId private constructor(
    val id: Long,
) {
    companion object {
        fun create() = UserFollowChannelId(IdWorker.DEFAULT.nextId())

        fun from(channelId: Long) = UserFollowChannelId(channelId)

        operator fun invoke(id: Long): UserFollowChannelId = from(id)
    }
}
