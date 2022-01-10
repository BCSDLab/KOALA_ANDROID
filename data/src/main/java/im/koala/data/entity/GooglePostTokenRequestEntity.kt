package im.koala.data.entity

import com.google.gson.annotations.SerializedName

class GooglePostTokenRequestEntity(
    @SerializedName("client_id")
    var clientId: String,
    @SerializedName("client_secret")
    var clientSecret: String,
    @SerializedName("code")
    var code: String,
    @SerializedName("grant_type")
    var grantType: String,
    @SerializedName("redirect_uri")
    var redirectUri: String
)