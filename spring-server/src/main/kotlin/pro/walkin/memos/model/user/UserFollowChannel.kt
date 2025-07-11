package pro.walkin.memos.model.user

import java.time.LocalDateTime

data class UserFollowChannel(
    val uid: UserFollowChannelId,
    val userId: UserId,
    val channelId: UserChannelId,
    val channelOwner: UserId,
    val followStartAt: LocalDateTime,
)

@JvmInline
value class UserFollowChannelId private constructor(
    val id: Long,
) {
    companion object {
        fun from(channelId: Long) = UserFollowChannelId(channelId)

        operator fun invoke(id: Long): UserFollowChannelId = from(id)
    }
}
