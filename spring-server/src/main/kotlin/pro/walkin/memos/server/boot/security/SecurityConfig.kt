package pro.walkin.memos.server.boot.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import pro.walkin.memos.server.boot.security.authentication.ReactiveTokenAuthenticationManager
import pro.walkin.memos.server.boot.security.authentication.ServerHttpTokenAuthenticationConverter
import pro.walkin.memos.server.boot.security.authentication.TokenAuthenticationProvider

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    companion object {
        val EXCLUDED_PATHS = arrayOf("/login", "/", "/static/**", "/index.html", "/favicon.ico")
    }

    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        jwtService: JWTService,
    ): SecurityWebFilterChain =
        http {
            authorizeExchange {
                authorize("/login", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)
                authorize(anyExchange, authenticated)
            }
            csrf {
                disable()
            }
            cors {
                disable()
            }
            formLogin {
                disable()
            }
            httpBasic {
                disable()
            }
            exceptionHandling {
                authenticationEntryPoint = HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)
            }

            logout {
                logoutUrl = "/api/auth/signOut"
            }
            // 认证
            addFilterAt(
                AuthenticationWebFilter(ReactiveTokenAuthenticationManager(jwtService)).apply {
                    this.setServerAuthenticationConverter(ServerHttpTokenAuthenticationConverter())
                },
                SecurityWebFiltersOrder.AUTHENTICATION,
            )

            securityContextRepository = NoOpServerSecurityContextRepository.getInstance()
        }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun tokenAuthenticationProvider(jwtService: JWTService) = TokenAuthenticationProvider(jwtService)
}
