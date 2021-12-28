package im.koala.data.entity

import com.google.gson.annotations.SerializedName

class TokenEntity {
    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("refresh_token")
    var refreshToken: String? = null
}
class TokenBodyEntity : CommonEntity() {
    @SerializedName("body")
    var body: TokenEntity? = null
}