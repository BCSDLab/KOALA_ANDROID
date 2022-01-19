package im.koala.data.api.response

import com.google.gson.annotations.SerializedName

data class KeywordResponse(
    @SerializedName("alarmCycle")
    var alarmCycle : Int = 0,
    @SerializedName("alarmMode")
    var alarmMode : Int = 0,
    @SerializedName("isImportant")
    var isImportant : Int = 0,
    @SerializedName("name")
    var name:String = "",
    @SerializedName("siteList")
    var siteList:List<String> = listOf()
)