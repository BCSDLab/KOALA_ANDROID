package im.koala.bcsd.ui.keywordadd

data class KeywordAddUiState(
    val keyword: String = "",
    val site: String = "",
    val editKeyword: String = "",
    val alarmCycle: Int = 0,
    val isImportant: Boolean = true,
    val silentMode: Boolean = true,
    val vibrationMode: Boolean = true,
    val untilPressOkButton: Boolean = true,
    val recentSearchList: List<String> = emptyList(),
    val recommendationList: List<String> = emptyList(),
    val searchList: List<String> = emptyList(),
    val siteList: List<String> = emptyList(),
    val nameList: List<String> = emptyList(),
)