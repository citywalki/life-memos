package pro.walkin.memos.model.user

import pro.walkin.memos.model.RowStatus

data class UserChannel(
    val uid: UserChannelId,
    val ownerUser: UserId,
    val description: UserChannelDescription?,
    var rowStatus: RowStatus? = RowStatus.NORMAL,
    val name: ChannelName,
    val isDefaultChannel: Boolean = false,
)

@JvmInline
value class UserChannelDescription(
    val description: String,
) {
    companion object {
        fun from(description: String) = UserChannelDescription(description)

        operator fun invoke(description: String) = UserChannelDescription(description)
    }
}

@JvmInline
value class UserChannelId private constructor(
    val value: Long,
) {
    companion object {
        fun from(channelId: Long) = UserChannelId(channelId)

        operator fun invoke(channelId: Long) = UserChannelId(channelId)
    }
}

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
