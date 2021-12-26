package im.koala.data.api.request.signup

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("account") val account: String,
    @SerializedName("find_email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("password") val password: String
)