package pro.walkin.memos.server.boot.security.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import pro.walkin.memos.server.boot.security.JWTService

class TokenAuthenticationProvider(
    jwtService: JWTService,
) : AbstractTokenAuthenticationManager(jwtService),
    AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication? = authentication?.let { super.authenticationToken(it) }

    override fun supports(authentication: Class<*>?): Boolean = JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
}
