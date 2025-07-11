package pro.walkin.memos.server.boot.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import pro.walkin.memos.model.user.UserName
import java.util.Date

@Service
class JWTService(
    @Value("\${app.secret}") val secret: String,
    @Value("\${app.refresh}") val refresh: String,
) {
    fun accessToken(
        username: UserName,
        roles: Array<String>,
    ): String = generate(username.value, 100 * 24 * 60 * 60, roles, secret)

    fun decodeAccessToken(accessToken: String): DecodedJWT = decode(secret, accessToken)

    fun refreshToken(
        username: String,
        expirationInMillis: Int,
        roles: Array<String>,
    ): String = generate(username, expirationInMillis, roles, refresh)

    fun decodeRefreshToken(refreshToken: String): DecodedJWT = decode(refresh, refreshToken)

    fun getRoles(decodedJWT: DecodedJWT) =
        decodedJWT
            .getClaim("role")
            .asList(String::class.java)
            .map { SimpleGrantedAuthority(it) }

    private fun generate(
        username: String,
        expirationInMillis: Int,
        roles: Array<String>,
        signature: String,
    ): String =
        JWT
            .create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationInMillis))
            .withArrayClaim("role", roles)
            .sign(Algorithm.HMAC512(signature.toByteArray()))

    private fun decode(
        signature: String,
        token: String,
    ): DecodedJWT =
        JWT
            .require(Algorithm.HMAC512(signature.toByteArray()))
            .build()
            .verify(token.replace("Bearer ", ""))
}
