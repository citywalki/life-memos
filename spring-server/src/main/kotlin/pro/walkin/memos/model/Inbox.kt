package pro.walkin.memos.model

enum class InboxStatus {
    UNREAD,
    ARCHIVED,
}

enum class InboxMessageType {
    TYPE_UNSPECIFIED,
    MEMO_COMMENT,
    VERSION_UPDATE,
}

data class InboxMessage(
    val type: InboxMessageType,
    val activityId: Long? = null,
)
