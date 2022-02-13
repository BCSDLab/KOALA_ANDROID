package im.koala.domain.repository

import im.koala.domain.state.NetworkState

interface KeywordAddRepository {
    suspend fun pushKeyword(
        alarmCycle: Int,
        alarmMode: Boolean,
        isImportant: Boolean,
        name: String,
        untilPressOkButton: Boolean,
        vibrationMode: Boolean,
        alarmSiteList: List<String>?
    ): NetworkState

    suspend fun deleteKeyword(keyword: String): NetworkState
    suspend fun getKeywordRecommendation(): NetworkState
    suspend fun getKeywordSiteRecommendation(): NetworkState
    suspend fun getKeywordSiteSearch(site: String): NetworkState
    suspend fun getKeywordSearch(keyword: String): NetworkState
    suspend fun getRecentSearchList(key: String): List<String>
    suspend fun setRecentSearchList(key: String, recentSearchList: List<String>)
}