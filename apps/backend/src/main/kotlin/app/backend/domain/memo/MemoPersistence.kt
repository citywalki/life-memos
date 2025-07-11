package app.backend.domain.memo

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import model.MemosVisibility
import model.RowStatus
import model.TableId

@Serializable
data class Memo(
    val uid: TableId,
    var rowStatus: RowStatus? = RowStatus.NORMAL,
    var creator: TableId,
    var content: String,
    var payload: MemoPayload = MemoPayload(),
    var visibility: MemosVisibility = MemosVisibility.PRIVATE,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)

@Serializable
data class MemoPayload(
    val tags: Set<String> = mutableSetOf(),
    val property: Property = Property(),
    val location: Location = Location(),
) {
    @Serializable
    data class Property(
        var hasLink: Boolean = false,
        var hasTaskList: Boolean = false,
        var hasCode: Boolean = false,
        var hasIncompleteTasks: Boolean = false,
        var references: List<String> = mutableListOf(),
    )

    @Serializable
    data class Location(
        val placeholder: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null,
    )
}
