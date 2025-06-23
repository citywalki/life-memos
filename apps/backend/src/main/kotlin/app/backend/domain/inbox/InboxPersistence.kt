package app.backend.domain.inbox

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import model.InboxMessage
import model.InboxStatus

@Serializable
data class Inbox(
    val id: Long,
    val version: Int = 0,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val senderId: Long? = null,
    val receiverId: Long? = null,
    val status: InboxStatus,
    val message: InboxMessage? = null,
)

object InboxDAO
