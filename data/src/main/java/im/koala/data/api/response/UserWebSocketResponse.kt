package im.koala.data.api.response

import com.google.gson.annotations.SerializedName

data class UserWebSocketResponse(
    @SerializedName("socket_token") val socketToken: String
)
