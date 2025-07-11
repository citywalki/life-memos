package pro.walkin.memos.model.auth

data class AuthTokens(
    private val accessToken: String,
    private val refreshToken: String,
)
