package im.koala.domain.repository

import im.koala.domain.entity.history.HistoryNotice
import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.state.Result

interface HistoryRepository {
    suspend fun getHistory(): List<HistoryNotice>
    suspend fun getHistoryByFilter(isRead: Boolean): List<HistoryNotice>
    suspend fun deleteHistory(noticeId: List<Int>): Result
    suspend fun undoDeleteHistory(noticeId: List<Int>): Result
    suspend fun scrapHistory(crawlingIdList: List<Int>): List<Int>
    suspend fun deleteScrap(crawlingIdList: List<Int>): Result
    suspend fun getScrapWithMemo(): List<ScrapNotice>
    suspend fun postMemo(userScrapId: Int, memo: String): Result
    suspend fun patchMemo(userScrapId: Int, memo: String): Result
}