package im.koala.data.api.response.keywordadd

import com.google.gson.annotations.SerializedName

data class KeywordAddResponse(
    @SerializedName("alarmCycle")
    val alarmCycle: Int,
    @SerializedName("isImportant")
    val isImportant: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("silentMode")
    val silentMode: Int,
    @SerializedName("siteList")
    val siteList: List<String>,
    @SerializedName("untilPressOkButton")
    val untilPressOkButton: Int,
    @SerializedName("vibrationMode")
    val vibrationMode: Int,
)

data class KeywordAddResponseEntity(
    @SerializedName("body")
    val body: KeywordAddResponse,
    @SerializedName("code")
    val code: Int
)