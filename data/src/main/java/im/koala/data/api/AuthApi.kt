package im.koala.data.api

import im.koala.data.api.response.ResponseWrapper
import im.koala.data.constant.KOALA_API_KEYWORD_RECOMMENDATION
import im.koala.data.constant.KOALA_API_KEYWORD_SEARCH
import im.koala.data.constant.KOALA_API_KEYWORD_SITE_RECOMMENDATION
import im.koala.data.constant.KOALA_API_KEYWORD_SITE_SEARCH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi{
    @GET(KOALA_API_KEYWORD_SEARCH)
    suspend fun getKeywordSearch(
        @Query("keyword") keyword: String
    ): ResponseWrapper<List<String>>

    @GET(KOALA_API_KEYWORD_SITE_SEARCH)
    suspend fun getKeywordSiteSearch(
        @Query("site") site: String
    ): ResponseWrapper<List<String>>

    @GET(KOALA_API_KEYWORD_SITE_RECOMMENDATION)
    suspend fun getKeywordSiteRecommendation(): ResponseWrapper<List<String>>

    @GET(KOALA_API_KEYWORD_RECOMMENDATION)
    suspend fun getKeywordRecommendation(): ResponseWrapper<List<String>>
import im.koala.data.constants.KEYWORD
import im.koala.data.entity.KeywordBodyEntity
import retrofit2.Response
import retrofit2.http.GET

interface AuthApi {
    @GET(KEYWORD)
    suspend fun getKeyword(): Response<KeywordBodyEntity>
}
