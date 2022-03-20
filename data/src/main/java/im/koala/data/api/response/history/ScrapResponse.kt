package im.koala.data.api.response.history

import com.google.gson.annotations.SerializedName

data class ScrapResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userScrapId")
    val userScrapId: Int,
    @SerializedName("site")
    val site: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("crawlingAt")
    val crawlingAt: String
)
