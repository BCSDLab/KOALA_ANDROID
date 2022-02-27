package im.koala.domain.repository

import im.koala.domain.state.Result

interface KeywordAddRepository {
    suspend fun pushKeyword(
        alarmCycle: Int,
        alarmMode: Boolean,
        isImportant: Boolean,
        name: String,
        untilPressOkButton: Boolean,
        vibrationMode: Boolean,
        alarmSiteList: List<String>?
    ): Result

    suspend fun editKeyword(
        keyword: String,
        alarmCycle: Int,
        alarmMode: Boolean,
        isImportant: Boolean,
        name: String,
        untilPressOkButton: Boolean,
        vibrationMode: Boolean,
        alarmSiteList: List<String>?
    ): Result

    suspend fun deleteKeyword(keyword: String): Result
    suspend fun getKeywordRecommendation(): Result
    suspend fun getKeywordSiteRecommendation(): Result
    suspend fun getKeywordSiteSearch(site: String): Result
    suspend fun getKeywordSearch(keyword: String): Result
    suspend fun getRecentSearchList(key: String): List<String>
    suspend fun setRecentSearchList(key: String, recentSearchList: List<String>)
    suspend fun getKeywordDetails(keyword: String): Result
}