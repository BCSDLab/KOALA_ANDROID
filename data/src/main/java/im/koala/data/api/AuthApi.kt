package im.koala.data.api

import im.koala.data.api.response.ResponseWrapper
import im.koala.data.constant.KOALA_API_KEYWORD_RECOMMENDATION
import im.koala.data.constant.KOALA_API_KEYWORD_SITE_RECOMMENDATION
import im.koala.data.constant.KOALA_API_KEYWORD_SITE_SEARCH
import im.koala.data.constant.KOALA_API_URL_KEYWORD
import im.koala.data.constant.KOALA_API_KEYWORD_SEARCH
import retrofit2.Response
import im.koala.data.constants.KEYWORD
import im.koala.data.entity.KeywordBodyEntity
import im.koala.domain.model.KeywordAddResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.PATCH


interface AuthApi {
    @GET(KOALA_API_KEYWORD_SEARCH)
    suspend fun getKeywordSearch(
        @Query("keyword") keyword: String
    ): Response<ResponseWrapper<List<String>>>

    @GET(KOALA_API_KEYWORD_SITE_SEARCH)
    suspend fun getKeywordSiteSearch(
        @Query("site") site: String
    ): Response<ResponseWrapper<List<String>>>

    @GET(KOALA_API_KEYWORD_SITE_RECOMMENDATION)
    suspend fun getKeywordSiteRecommendation(): Response<ResponseWrapper<List<String>>>

    @GET(KOALA_API_KEYWORD_RECOMMENDATION)
    suspend fun getKeywordRecommendation(): Response<ResponseWrapper<List<String>>>

    @GET(KEYWORD)
    suspend fun getKeyword(): Response<KeywordBodyEntity>

    @POST(KOALA_API_URL_KEYWORD)
    suspend fun pushKeyword(
        @Body keywordAddResponse: KeywordAddResponse
    ): Response<ResponseWrapper<String>>

    @PATCH(KOALA_API_URL_KEYWORD)
    suspend fun deleteKeyword(
        @Query("keyword-name") keyword: String
    ): Response<ResponseWrapper<String>>

}
