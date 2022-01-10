package im.koala.data.api.response.signup

import com.google.gson.annotations.SerializedName

data class SignUpResultResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("account") val account: String,
    @SerializedName("find_email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("user_type") val userType: Int,
    @SerializedName("is_auth") val isAuth: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)