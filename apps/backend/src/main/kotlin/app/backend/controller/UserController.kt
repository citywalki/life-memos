package app.backend.controller

import app.backend.dao.UserChannelDAO
import app.backend.dao.UserDAO
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.response.respond
import io.ktor.server.response.respondNullable
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import kotlinx.datetime.Clock
import model.auth.UserSession
import model.user.UserId
import model.user.UserName

suspend fun Application.userRoutes() {
    val userDAO = dependencies.resolve<UserDAO>()
    val userChannelDAO = dependencies.resolve<UserChannelDAO>()
    routing {
        route("/api") {
            get("/me") {
                val userSession = call.sessions.get<UserSession>()
                val redirect = call.queryParameters["redirect"]
                if (userSession == null) {
                    call.respondRedirect("/login" + if (redirect != null) "?redirect=$redirect" else "")
                } else {
                    // todo emailVerified
                    val userId = userSession.userId

                    val user = userDAO.findUser(userId)
                    if (user == null || userSession.expirationTime < Clock.System.now()) {
                        call.respondRedirect("/login")
                    } else {
                        call.respond(user)
                    }
                }
            }

            authenticate {
                route("/user") {
                    get {
                        val username = call.parameters["username"]
                        val userId = call.parameters["userId"]

                        val user =
                            if (userId != null) {
                                userDAO.findUser(UserId(userId))
                            } else if (username != null) {
                                userDAO.findUser(UserName(username))
                            } else {
                                null
                            }

                        call.respondNullable(user)
                    }

                    route("/channels") {
                        get {
                            val username = call.parameters["username"]
                            val userId = call.parameters["userId"]

                            if (userId != null) {
                                val userChannels = userChannelDAO.findChannels(UserId(userId))
                                call.respond(userChannels)
                            }
                        }
                    }
                }
            }
        }
    }
}
