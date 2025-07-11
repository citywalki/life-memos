package pro.walkin.memos.server.boot.security.authentication

import kotlinx.coroutines.reactor.mono
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class ServerHttpTokenAuthenticationConverter : ServerAuthenticationConverter {
    companion object {
        const val TOKEN_PREFIX = "Bearer "
    }

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication?> =
        mono {
            val request = exchange?.request
            val authorization = request?.headers?.getFirst(HttpHeaders.AUTHORIZATION)
            if (authorization == null || !authorization.startsWith(TOKEN_PREFIX)) {
                null
            } else {
                JwtAuthenticationToken("", authorization)
            }
        }
}
