package im.koala.data.repository.remote

import im.koala.data.api.response.ResponseWrapper
import im.koala.data.entity.KeywordBodyEntity
import im.koala.data.entity.TokenBodyEntity
import im.koala.domain.model.KeywordAddResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getKeyword(): Response<KeywordBodyEntity>
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): Response<TokenBodyEntity>
    suspend fun pushKeyword(keywordAddResponse: KeywordAddResponse): Response<ResponseWrapper<String>>
    suspend fun getKeywordRecommendation(): Response<ResponseWrapper<List<String>>>
    suspend fun getKeywordSiteRecommendation(): Response<ResponseWrapper<List<String>>>
    suspend fun getKeywordSiteSearch(site: String): Response<ResponseWrapper<List<String>>>
    suspend fun getKeywordSearch(keyword: String): Response<ResponseWrapper<List<String>>>
}