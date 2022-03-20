package im.koala.domain.repository

import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.state.Result

interface HistoryRepository {
    suspend fun getHistory(): List<KeywordNotice>
    suspend fun getHistoryByFilter(isRead: Boolean): List<KeywordNotice>
    suspend fun deleteHistory(noticeId: List<Int>): Result
    suspend fun undoDeleteHistory(noticeId: List<Int>): Result
    suspend fun scrapHistory(crawlingIdList: List<Int>): List<Int>
    suspend fun deleteScrap(crawlingIdList: List<Int>): Result
    suspend fun getScrapWithMemo(): List<ScrapNotice>
}