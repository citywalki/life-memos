package pro.walkin.memos.server.controller.user

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import pro.walkin.memos.model.user.User
import pro.walkin.memos.model.user.UserChannel
import pro.walkin.memos.model.user.UserId
import pro.walkin.memos.model.user.UserName
import pro.walkin.memos.server.usecase.user.UserQuery
import reactor.netty.http.server.HttpServerResponse

@Controller
class UserController(
    private val userQuery: UserQuery,
) {
    suspend fun me(serverResponse: HttpServerResponse) {
        serverResponse.sendRedirect("").block()

        // val userSession = call.sessions.get<UserSession>()
        //                val redirect = call.queryParameters["redirect"]
        //                if (userSession == null) {
        //                    call.respondRedirect("/login" + if (redirect != null) "?redirect=$redirect" else "")
        //                } else {
        //                    // todo emailVerified
        //                    val userId = userSession.userId
        //
        //                    val user = userDAO.findUser(userId)
        //                    if (user == null || userSession.expirationTime < Clock.System.now()) {
        //                        call.respondRedirect("/login")
        //                    } else {
        //                        call.respond(user)
        //                    }
        //                }
    }

    @QueryMapping
    suspend fun users(): List<User> {
        val users = userQuery.findUsers()

        return users
    }

    @QueryMapping
    suspend fun user(
        @Argument userId: String?,
        @Argument userName: String?,
    ): User? =
        if (userId != null) {
            userQuery.findUser(UserId(userId))
        } else if (userName != null) {
            userQuery.findUser(UserName(userName))
        } else {
            null
        }

    @SchemaMapping
    fun followChannels(
        user: User,
        @Argument limit: Int?,
    ): List<UserChannel> = emptyList()

    @SchemaMapping
    fun hostChannels(
        user: User,
        @Argument limit: Int?,
    ): List<UserChannel> = emptyList()
}
