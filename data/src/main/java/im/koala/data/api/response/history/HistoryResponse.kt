package im.koala.data.api.response.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("site")
    val site: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("isRead")
    val isRead: Boolean,
    @SerializedName("crawlingId")
    val crawlingId: Int
)