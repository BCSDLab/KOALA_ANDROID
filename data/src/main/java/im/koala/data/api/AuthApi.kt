package im.koala.data.api

import im.koala.data.api.response.keyword.KeywordNoticeResponse
import im.koala.data.constant.KOALA_API_URL_KEYWORD_LIST
import im.koala.data.constant.KOALA_API_URL_KEYWORD_LIST_NOTICE
import im.koala.data.constant.KOALA_API_URL_KEYWORD_LIST_NOTICE_READING_CHECK
import im.koala.data.constant.KOALA_API_URL_KEYWORD_LIST_SEARCH
import im.koala.data.entity.CommonEntity
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface AuthApi {
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
}