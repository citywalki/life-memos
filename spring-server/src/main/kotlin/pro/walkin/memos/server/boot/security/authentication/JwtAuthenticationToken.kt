package pro.walkin.memos.server.boot.security.authentication

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthenticationToken(
    val username: String?,
    val token: String,
    authorities: List<GrantedAuthority> = emptyList(),
) : AbstractAuthenticationToken(
        authorities,
    ) {
    override fun getCredentials(): Any = token

    override fun getPrincipal(): Any? = username
}
