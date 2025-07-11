package model.user

import kotlinx.serialization.Serializable
import model.RowStatus
import shared.infra.IdWorker

@Serializable
data class UserChannel(
    val uid: UserChannelId,
    val ownerUser: UserId,
    val description: UserChannelDescription?,
    var rowStatus: RowStatus? = RowStatus.NORMAL,
    val name: ChannelName,
    val isDefaultChannel: Boolean = false,
)

@Serializable
@JvmInline
value class UserChannelDescription(
    val description: String,
) {
    companion object {
        fun from(description: String) = UserChannelDescription(description)

        operator fun invoke(description: String) = UserChannelDescription(description)
    }
}

@Serializable
@JvmInline
value class UserChannelId private constructor(
    val value: Long,
) {
    companion object {
        fun create() = UserChannelId(IdWorker.DEFAULT.nextId())

        fun from(channelId: Long) = UserChannelId(channelId)

        operator fun invoke(channelId: Long) = UserChannelId(channelId)
    }
}

@Serializable
@JvmInline
value class ChannelName(
    val value: String,
) {
    // 不允许直接构建
    private constructor() : this("")

    companion object {
        // todo name check
        fun from(value: String) = ChannelName(value)
    }
}
