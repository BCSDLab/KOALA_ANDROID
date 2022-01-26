package im.koala.data.entity

import com.google.gson.annotations.SerializedName

data class KeywordEntity(
    @SerializedName("id")
    var id: Int = -1,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("alarmCycle")
    var alarmCycle: Int = -1,

    @SerializedName("noticeNum")
    var noticeNum: Int = -1

)
data class KeywordBodyEntity(
    @SerializedName("body")
    var body: List<KeywordEntity> = emptyList()
) : CommonEntity() {
    companion object {
        val SUCCESS = KeywordBodyEntity().apply {
            code = 200
            body = emptyList()
        }
        val FAIL = KeywordBodyEntity().apply {
            code = 315
            errorMessage = "fail"
        }
    }
}