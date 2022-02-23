package im.koala.data.repository.remote

import im.koala.data.api.cc
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.api.response.keywordadd.KeywordAddResponse
import im.koala.data.api.response.keywordadd.KeywordAddResponseEntity
import im.koala.data.api.tt
import retrofit2.Response

interface KeywordAddRemoteDataSource {
    suspend fun pushKeyword(keywordAddResponse: KeywordAddResponse): Response<ResponseWrapper<String>>
    suspend fun editKeyword(
        keyword: String,
        keywordAddResponse: KeywordAddResponse
    ): Response<ResponseWrapper<String>>
    suspend fun deleteKeyword(keyword: String): Response<ResponseWrapper<String>>
    suspend fun getKeywordRecommendation(): Response<ResponseWrapper<List<String>>>
    suspend fun getKeywordSiteRecommendation(): Response<ResponseWrapper<List<String>>>
    suspend fun getKeywordSiteSearch(site: String): Response<ResponseWrapper<List<String>>>
    suspend fun getKeywordSearch(keyword: String): Response<ResponseWrapper<List<String>>>
    suspend fun getKeywordDetails(keyword: String): Response<KeywordAddResponseEntity>
}