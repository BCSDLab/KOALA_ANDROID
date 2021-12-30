package im.koala.domain.model

data class TokenResponse(
    var accessToken: String = "",
    var refreshToken: String = ""
) : CommonResponse() {
    /* 테스트코드에 사용 */
    companion object {
        val SUCCESS = TokenResponse().apply {
            accessToken = "accessToken"
            refreshToken = "refreshToken"
        }
    }
}