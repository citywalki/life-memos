package app.backend.controller

import app.backend.dao.UserChannelDAO
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import model.user.ChannelName
import model.user.UserChannelId

suspend fun Application.userChannelRoutes() {
    val userChannelDAO = dependencies.resolve<UserChannelDAO>()
    routing {
        route("/api") {
            authenticate {
                get("/channels") {
                    val userChannelId = call.parameters["userChannelId"]?.toLong()
                    val userChannelName = call.parameters["userChannelName"]

                    userChannelDAO.findChannel(
                        userChannelId?.let { UserChannelId(it) },
                        userChannelName?.let { ChannelName(it) },
                    )
                }
            }
        }
    }
}
