package pro.walkin.memos.model

enum class RowStatus {
    NORMAL,
    ARCHIVED,
}

typealias TableId = String

typealias PageToken = String

data class ArgumentErrorMessage(
    val field: String,
    val message: String?,
)

data class ErrorResponse(
    val code: Int,
    val message: String? = null,
    val fields: List<ArgumentErrorMessage>? = null,
) {
    object ErrorResponseCode {
        const val FIELD_ERROR = 1000
    }
}
