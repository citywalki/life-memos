package app.backend.common

import io.ktor.server.auth.principal
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.RoutingContext
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import model.auth.UserSession
import org.komapper.r2dbc.R2dbcDatabase
import org.slf4j.MDC

suspend fun <R> RoutingContext.withBizContext(
    block: () -> R,
): R {
    val userSession = call.principal<UserSession>()
    val database = call.application.dependencies.resolve<R2dbcDatabase>()

    return if (userSession != null) {
        MDC.put("userId", userSession.userId.value.toString())
        withContext(MDCContext()) {
            database.withTransaction {
                block()
            }
        }
    } else {
        database.withTransaction {
            block()
        }
    }
}
