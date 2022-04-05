package im.koala.data.repository.remote

import im.koala.data.api.response.ResponseWrapper
import im.koala.domain.entity.history.HistoryNotice
import im.koala.domain.entity.history.Memo
import im.koala.domain.entity.history.ScrapNotice
import retrofit2.Response

interface HistoryRemoteDataSource {
    suspend fun getHistory(): List<HistoryNotice>

    suspend fun getHistoryByFilter(isRead: Boolean): List<HistoryNotice>

    suspend fun deleteHistory(noticeId: List<Int>): Response<ResponseWrapper<String>>

    suspend fun undoDeleteHistory(noticeId: List<Int>): Response<ResponseWrapper<String>>

    suspend fun scrapHistory(crawlingId: Int): Response<ResponseWrapper<String>>

    suspend fun deleteScrapHistory(crawlingIdList: List<Int>): Response<ResponseWrapper<String>>

    suspend fun getScrap(): List<ScrapNotice>

    suspend fun getMemo(): List<Memo>

    suspend fun postMemo(
        userScrapId: Int,
        memo: String
    ): Response<ResponseWrapper<String>>

    suspend fun patchMemo(
        userScrapId: Int,
        memo: String
    ): Response<ResponseWrapper<String>>
}