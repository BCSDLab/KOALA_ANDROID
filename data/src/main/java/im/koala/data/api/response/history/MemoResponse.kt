package im.koala.data.api.response.history

import com.google.gson.annotations.SerializedName

data class MemoResponse(
    @SerializedName("userScrapId")
    val userScrapId: Int,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)