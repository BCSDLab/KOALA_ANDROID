package im.koala.domain.model

data class TokenResponse(
    var accessToken: String = "",
    var refreshToken: String = ""
) : CommonResponse()