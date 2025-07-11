package pro.walkin.memos.server.controller

import com.fasterxml.jackson.databind.JsonMappingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.codec.CodecException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class GlobalRestExceptionHandler {
    val logger: Logger = LoggerFactory.getLogger("web")

    @ExceptionHandler(Exception::class)
    fun handle(exc: Exception): ResponseEntity<String> {
        logger.error(exc.message, exc)

        val message = extractExceptionMessage(exc)

        return ResponseEntity.internalServerError().body(message)
    }

    private fun extractExceptionMessage(e: Throwable): String =
        when (e) {
            is ServerWebInputException -> extractExceptionMessage(e.cause!!)
            is CodecException -> extractExceptionMessage(e.cause!!)
            is JsonMappingException -> extractExceptionMessage(e.cause!!)
            else -> e.message!!
        }
}
