package pro.walkin.memos.server.boot.security.authentication

import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import pro.walkin.memos.server.boot.security.JWTService
import reactor.core.publisher.Mono

class ReactiveTokenAuthenticationManager(
    jwtService: JWTService,
) : AbstractTokenAuthenticationManager(jwtService),
    ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication?> =
        mono {
            authentication?.let { authenticationToken(it) }
        }
}
