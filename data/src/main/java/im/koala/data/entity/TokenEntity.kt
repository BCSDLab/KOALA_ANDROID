package im.koala.data.entity

import com.google.gson.annotations.SerializedName

data class TokenEntity(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String
)

class TokenBodyEntity(
    @SerializedName("body")
    val body: TokenEntity?
) : CommonEntity() {
    companion object {
        val SUCCESS = TokenBodyEntity(
            TokenEntity(
                accessToken = "accessToken",
                refreshToken = "refreshToken"
            )
        ).apply {
            code = 200
        }
        val FAIL = TokenBodyEntity(null).apply {
            code = 315
            errorMessage = "fail"
        }
    }
}