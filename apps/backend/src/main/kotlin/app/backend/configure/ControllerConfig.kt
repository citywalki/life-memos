package app.backend.configure

import app.backend.controller.authRoutes
import app.backend.controller.userChannelRoutes
import app.backend.controller.userRoutes
import app.backend.error.ArgumentVerificationException
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.auth.authentication
import io.ktor.server.auth.session
import io.ktor.server.config.property
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import kotlinx.datetime.Clock
import model.ArgumentErrorMessage
import model.ErrorResponse
import model.ErrorResponse.ErrorResponseCode.FIELD_ERROR
import model.auth.UserSession

suspend fun Application.configureController() {
    configureOpenAPI()
    configureSerialization()
    configureWebLogging()
    configureWebErrorHandle()
    configureWebAuth()

    authRoutes()
    userRoutes()
    userChannelRoutes()
}

fun Application.configureWebErrorHandle() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.application.log.error(cause.message, cause)
            if (cause is ArgumentVerificationException) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse(FIELD_ERROR, fields = listOf(ArgumentErrorMessage(cause.field, cause.message))),
                )
            } else {
                call.respond(HttpStatusCode.InternalServerError, ErrorResponse(1, cause.message ?: "system error"))
            }
        }
    }
}

fun Application.configureWebAuth() {
    install(Sessions) {
        val encryptKey = this@configureWebAuth.property<String>("session.secret.encrypt")
        val authKey = this@configureWebAuth.property<String>("session.secret.encrypt")

        val secretEncryptKey = encryptKey.encodeToByteArray().copyOf(16)
        val secretAuthKey = authKey.encodeToByteArray()
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 7L * 24 * 3600 // 7 days
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }

    authentication {
        session<UserSession> {
            validate { session ->
                val expirationTime = session.expirationTime
                if (expirationTime > Clock.System.now()) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}

fun Application.configureWebLogging() {
    install(CallId) {
        header(HttpHeaders.XRequestId)
        verify { callId: String ->
            callId.isNotEmpty()
        }
    }
    install(CallLogging) {
        callIdMdc("call-id")
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
//        jackson {
//            registerModule(DatetimeJacksonModule.all)
//            registerModule(
//                SimpleModule()
//                    .addSerializer(Long::class.javaPrimitiveType, ToStringSerializer.instance)
//                    .addSerializer(Long::class.javaObjectType, ToStringSerializer.instance),
//            )
//            enable(SerializationFeature.INDENT_OUTPUT)
//        }
    }
}

fun Application.configureOpenAPI() {
//    if (developmentMode) {
    routing {
        swaggerUI(path = "openapi")
        openAPI(path = "openapi")
    }
//    }
}
