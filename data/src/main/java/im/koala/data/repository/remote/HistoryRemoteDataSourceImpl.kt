package im.koala.data.repository.remote

import im.koala.data.api.AuthApi
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.mapper.history.toHistoryNotice
import im.koala.data.mapper.history.toScrapNotice
import im.koala.domain.entity.history.HistoryNotice
import im.koala.domain.entity.history.Memo
import im.koala.domain.entity.history.ScrapNotice
import retrofit2.Response
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : HistoryRemoteDataSource {
    override suspend fun getHistory(): List<HistoryNotice> {
        return authApi.getHistory().body.map {
            it.toHistoryNotice()
        }
    }

    override suspend fun getHistoryByFilter(isRead: Boolean): List<HistoryNotice> {
        return authApi.getHistoryByFilter(isRead).body.map {
            it.toHistoryNotice()
        }
    }

    override suspend fun deleteHistory(noticeId: List<Int>): Response<ResponseWrapper<String>> {
        return authApi.deleteHistory(noticeId)
    }

    override suspend fun undoDeleteHistory(noticeId: List<Int>): Response<ResponseWrapper<String>> {
        return authApi.undoDeleteHistory(noticeId)
    }

    override suspend fun scrapHistory(crawlingId: Int): Response<ResponseWrapper<String>> {
        return authApi.scrapHistory(crawlingId)
    }

    override suspend fun deleteScrapHistory(crawlingIdList: List<Int>): Response<ResponseWrapper<String>> {
        return authApi.deleteScrapHistory(crawlingIdList)
    }

    override suspend fun getScrap(): List<ScrapNotice> {
        return authApi.getScrap().body.map {
            it.toScrapNotice()
        }
    }

    override suspend fun getMemo(): List<Memo> {
        return authApi.getMemo().body.map {
            Memo(
                userScrapedId = it.userScrapId,
                memo = it.memo,
                updatedAt = it.updatedAt,
                createdAt = it.createdAt
            )
        }
    }

    override suspend fun postMemo(
        userScrapId: Int,
        memo: String
    ): Response<ResponseWrapper<String>> {
        return authApi.postMemo(userScrapId, memo)
    }

    override suspend fun patchMemo(
        userScrapId: Int,
        memo: String
    ): Response<ResponseWrapper<String>> {
        return authApi.patchMemo(userScrapId, memo)
    }
}