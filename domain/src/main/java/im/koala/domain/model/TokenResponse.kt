package im.koala.domain.model

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
) : CommonResponse() {
    /* 테스트코드에 사용 */
    companion object {
        val SUCCESS = TokenResponse(
            accessToken = "accessToken",
            refreshToken = "refreshToken"
        )
    }
}