package app.backend.controller

import app.backend.dao.UserDAO
import app.backend.error.ArgumentVerificationException
import app.backend.error.userMessages
import app.backend.service.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import kotlinx.serialization.Serializable
import model.auth.UserSession
import model.user.HashedPassword
import model.user.UserName
import org.komapper.r2dbc.R2dbcDatabase
import pro.walkin.logging.I18nMessages

@Serializable
data class Signin(
    val username: String,
    val password: String,
    val neverExpire: Boolean? = false,
)

@Serializable
data class SignUp(
    val username: String,
    val password: String,
)

suspend fun Application.authRoutes() {
    val authService = dependencies.resolve<AuthService>()
    val userQuery = dependencies.resolve<UserDAO>()
    val database = dependencies.resolve<R2dbcDatabase>()

    routing {
        route("/api") {
            post("/auth/signin") {
                val signIn = call.receive<Signin>()
                database.withTransaction {
                    val username = UserName(signIn.username)
                    val password = HashedPassword(signIn.password)

                    if (userQuery.findUser(username) == null) {
                        throw ArgumentVerificationException("username", I18nMessages.userMessages.userNotExist())
                    }

                    val signedUser = authService.signin(username, password)
                    call.sessions.set(UserSession.create(signedUser.id))
                }
                call.respond(HttpStatusCode.OK)
            }

            post("/auth/signup") {
                val signUp = call.receive<SignUp>()
                database.withTransaction { _ ->
                    val username = UserName(signUp.username)
                    val password = HashedPassword(signUp.password)

                    if (userQuery.findUser(username) != null) {
                        throw ArgumentVerificationException("username", I18nMessages.userMessages.userSameExist())
                    }

                    val signedUser = authService.signup(username, password)

                    call.sessions.set(UserSession.create(signedUser.id))
                }
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
