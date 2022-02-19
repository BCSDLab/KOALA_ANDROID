package im.koala.data.api.response.keywordadd

data class KeywordAddResponseUi(
    var alarmCycle: Int = 0,
    var isImportant: Boolean = true,
    var name: String = "",
    var silentMode: Boolean = true,
    var siteList: List<String> = mutableListOf(),
    var untilPressOkButton: Boolean = true,
    var vibrationMode: Boolean = true,
)
