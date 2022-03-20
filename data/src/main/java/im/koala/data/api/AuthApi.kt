package im.koala.data.api

import im.koala.data.api.response.ResponseWrapper
import im.koala.data.api.response.history.HistoryResponse
import im.koala.data.api.response.history.MemoResponse
import im.koala.data.api.response.history.ScrapResponse
import im.koala.data.api.response.keyword.KeywordNoticeResponse
import im.koala.data.api.response.keywordadd.KeywordAddResponse
import im.koala.data.constant.*
import im.koala.data.constants.KEYWORD
import im.koala.data.entity.CommonEntity
import im.koala.data.entity.KeywordBodyEntity
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @GET(KEYWORD)
    suspend fun getKeyword(): Response<KeywordBodyEntity>

    @GET(KOALA_API_URL_KEYWORD_LIST)
    suspend fun getKeywordList(
        @Query("keyword-name") keywordName: String,
        @Query("site") site: String? = null
    ): List<KeywordNoticeResponse>

    @GET(KOALA_API_URL_KEYWORD_LIST_SEARCH)
    suspend fun searchKeywordList(
        @Query("word") search: String,
        @Query("keyword-name") keywordName: String,
        @Query("site") site: String? = null
    ): List<KeywordNoticeResponse>

    @PATCH(KOALA_API_URL_KEYWORD_LIST_NOTICE)
    suspend fun removeKeywordNotice(
        @Query("notice-id") noticeIds: List<Int>
    ): CommonEntity

    @PATCH(KOALA_API_URL_KEYWORD_LIST_NOTICE_READING_CHECK)
    suspend fun markAsReadKeywordNotice(
        @Query("notice-id") noticeId: Int
    ): CommonEntity

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

    @POST(KOALA_API_URL_KEYWORD)
    suspend fun pushKeyword(
        @Body keywordAddResponse: KeywordAddResponse
    ): Response<ResponseWrapper<String>>

    @PATCH(KOALA_API_URL_KEYWORD)
    suspend fun deleteKeyword(
        @Query("keyword-name") keyword: String
    ): Response<ResponseWrapper<String>>

    @GET(KOALA_API_HISTORY)
    suspend fun getHistory(): List<HistoryResponse>

    @GET(KOALA_API_HISTORY)
    suspend fun getHistoryByFilter(
        @Query("is-read") isRead: Boolean
    ): List<HistoryResponse>

    @PATCH(KOALA_API_HISTORY)
    suspend fun deleteHistory(
        @Query("notice-id") noticeId: List<Int>
    ): Response<ResponseWrapper<String>>

    @PATCH(KOALA_API_HISTORY_DELETE_UNDO)
    suspend fun undoDeleteHistory(
        @Query("notice-id") noticeId: List<Int>
    ): Response<ResponseWrapper<String>>

    @POST(KOALA_API_SCRAP)
    suspend fun scrapHistory(
        @Query("crawling_id") crawlingId: Int
    ): Response<ResponseWrapper<String>>

    @DELETE(KOALA_API_SCRAP)
    suspend fun deleteScrapHistory(
        @Query("crawlingId") crawlingId: List<Int>
    ): Response<ResponseWrapper<String>>

    @GET(KOALA_API_SCRAP)
    suspend fun getScrap(): List<ScrapResponse>

    @GET(KOALA_API_MEMO)
    suspend fun getMemo(): List<MemoResponse>
}