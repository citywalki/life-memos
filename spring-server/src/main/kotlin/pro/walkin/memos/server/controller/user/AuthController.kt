package pro.walkin.memos.server.controller.user

import io.swagger.v3.oas.annotations.Operation
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import pro.walkin.memos.model.user.UserName
import pro.walkin.memos.model.user.UserPassword
import pro.walkin.memos.server.boot.security.JWTService
import pro.walkin.memos.server.usecase.user.UserService

data class SignupRequest(
    val username: UserName,
    val password: UserPassword,
)

data class SigninRequest(
    val username: UserName,
    val password: UserPassword,
)

data class SigninResponse(
    val accessToken: String,
    val refreshToken: String,
)

@Controller
@Transactional
class AuthController(
    private val userService: UserService,
    private val jwtService: JWTService,
) {
    @Operation(summary = "用户登录")
    @PostMapping("/signin", produces = ["application/json"])
    @ResponseBody
    suspend fun signin(
        @RequestBody request: SigninRequest,
    ): SigninResponse {
        val user =
            userService.signinCheck(
                request.username,
                request.password,
            )

        val accessToken =
            jwtService.accessToken(
                user.username,
                arrayOf("user"),
            )
        return SigninResponse(
            accessToken = accessToken,
            refreshToken = "11",
        )
    }

    @Operation(summary = "用户注册")
    @PostMapping("/signup", produces = ["application/json"])
    @ResponseBody
    suspend fun signup(
        @RequestBody request: SignupRequest,
    ): SigninResponse {
        val user =
            userService.signup(
                request.username,
                request.password,
            )

        val accessToken =
            jwtService.accessToken(
                user.username,
                arrayOf("user"),
            )
        return SigninResponse(
            accessToken = accessToken,
            refreshToken = "11",
        )
    }
}
