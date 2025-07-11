package pro.walkin.memos.server.common

// data class ResponseEntity(
//    val message: String,
//    val success: Boolean,
// )

sealed class ResponseEntity(
    val message: String,
)
