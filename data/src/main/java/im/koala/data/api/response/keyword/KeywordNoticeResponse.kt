package im.koala.data.api.response.keyword

import com.google.gson.annotations.SerializedName

data class KeywordNoticeResponse(
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("id") val id: Int,
    @SerializedName("isRead") val isRead: Boolean,
    @SerializedName("site") val site: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)
