package im.koala.data.api

import im.koala.data.api.response.KeywordResponse
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.constant.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import im.koala.data.constants.KEYWORD
import im.koala.data.entity.KeywordBodyEntity

interface AuthApi {
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

    @GET(KEYWORD)
    suspend fun getKeyword(): Response<KeywordBodyEntity>

    @POST(KOALA_API_URL_KEYWORD)
    suspend fun pushKeyword(
        @Body keywordResponse: KeywordResponse
    ): Response<ResponseWrapper<String>>

}
