package im.koala.domain.repository

import im.koala.bcsd.state.NetworkState
import im.koala.domain.model.KeywordAddResponse

interface KeywordAddRepository {
    suspend fun pushKeyword(keywordResponse: KeywordAddResponse): NetworkState
    suspend fun getKeywordRecommendation(): NetworkState
    suspend fun getKeywordSiteRecommendation(): NetworkState
    suspend fun getKeywordSiteSearch(site: String): NetworkState
    suspend fun getKeywordSearch(keyword: String): NetworkState
}