package im.koala.data.repository

import im.koala.data.repository.remote.HistoryRemoteDataSource
import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.repository.HistoryRepository
import im.koala.domain.state.Result
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyRemoteDataSource: HistoryRemoteDataSource
) : HistoryRepository {
    override suspend fun getHistory(): List<KeywordNotice> {
        return historyRemoteDataSource.getHistory()
    }

    override suspend fun getHistoryByFilter(isRead: Boolean): List<KeywordNotice> {
        return historyRemoteDataSource.getHistoryByFilter(isRead)
    }

    override suspend fun deleteHistory(noticeId: List<Int>): Result {
        val response = historyRemoteDataSource.deleteHistory(noticeId)
        return if (response.isSuccessful) {
            if (response.body()?.code == 200) {
                Result.Success(response.body()?.body)
            } else {
                Result.Fail(response.body()?.body)
            }
        } else Result.Fail(response.errorBody().toString())
    }

    override suspend fun undoDeleteHistory(noticeId: List<Int>): Result {
        val response = historyRemoteDataSource.undoDeleteHistory(noticeId)
        return if (response.isSuccessful) {
            if (response.body()?.code == 200) {
                Result.Success(response.body()?.body)
            } else {
                Result.Fail(response.body()?.body)
            }
        } else Result.Fail(response.errorBody().toString())
    }

    override suspend fun scrapHistory(crawlingIdList: List<Int>): List<Int> {
        val successList = mutableListOf<Int>()
        for (crawlingId in crawlingIdList) {
            val response = historyRemoteDataSource.scrapHistory(crawlingId)
            if (response.isSuccessful && response.body()?.code == 200)
                successList.add(crawlingId)
        }
        return successList
    }

    override suspend fun deleteScrap(crawlingIdList: List<Int>): Result {
        val response = historyRemoteDataSource.deleteScrapHistory(crawlingIdList)
        return if (response.isSuccessful) {
            if (response.body()?.code == 200) {
                Result.Success(response.body()?.body)
            } else {
                Result.Fail(response.body()?.body)
            }
        } else Result.Fail(response.errorBody().toString())
    }

    override suspend fun getScrapWithMemo(): List<ScrapNotice> {
        val memoList = historyRemoteDataSource.getMemo()
        return historyRemoteDataSource.getScrap().map { scrapNotice ->
            scrapNotice.copy(
                memoCheck = memoList.find {
                    it.userScrapedId == scrapNotice.userScrapedId
                }
            )
        }
    }
}
