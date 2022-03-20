package im.koala.data.repository.remote

import im.koala.data.api.AuthApi
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.mapper.history.toKeywordNotice
import im.koala.data.mapper.history.toScrapNotice
import im.koala.domain.entity.history.Memo
import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.entity.keyword.KeywordNotice
import retrofit2.Response
import javax.inject.Inject

class HistoryRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : HistoryRemoteDataSource {
    override suspend fun getHistory(): List<KeywordNotice> {
        return authApi.getHistory().map {
            it.toKeywordNotice()
        }
    }

    override suspend fun getHistoryByFilter(isRead: Boolean): List<KeywordNotice> {
        return authApi.getHistoryByFilter(isRead).map {
            it.toKeywordNotice()
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
        return authApi.getScrap().map {
            it.toScrapNotice()
        }
    }

    override suspend fun getMemo(): List<Memo> {
        return authApi.getMemo().map {
            Memo(
                userScrapedId = it.userScrapId,
                memo = it.memo,
                updatedAt = it.updatedAt,
                createdAt = it.createdAt
            )
        }
    }
}