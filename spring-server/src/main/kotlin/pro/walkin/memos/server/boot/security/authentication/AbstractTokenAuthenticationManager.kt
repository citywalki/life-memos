package pro.walkin.memos.server.boot.security.authentication

import org.springframework.security.core.Authentication
import pro.walkin.memos.server.boot.security.JWTService

open class AbstractTokenAuthenticationManager(
    private val jwtService: JWTService,
) {
    fun authenticationToken(authentication: Authentication): JwtAuthenticationToken? {
        val jwtAuthenticationToken: JwtAuthenticationToken = authentication as JwtAuthenticationToken

        try {
            val token = jwtService.decodeAccessToken(jwtAuthenticationToken.token)

            return JwtAuthenticationToken(token.subject, token.token).apply {
                this.isAuthenticated = true
            }
        } catch (e: Exception) {
            // ignore
        }

        return null
    }
}
