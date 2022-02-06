package im.koala.domain.repository

import android.content.Context
import im.koala.domain.state.NetworkState
import im.koala.domain.model.KeywordAddResponse

interface KeywordAddRepository {
    suspend fun pushKeyword(keywordResponse: KeywordAddResponse): NetworkState
    suspend fun getKeywordRecommendation(): NetworkState
    suspend fun getKeywordSiteRecommendation(): NetworkState
    suspend fun getKeywordSiteSearch(site: String): NetworkState
    suspend fun getKeywordSearch(keyword: String): NetworkState
    suspend fun getRecentSearchList(key:String):List<String>
    suspend fun setRecentSearchList(key:String,recentSearchList:List<String>)
}