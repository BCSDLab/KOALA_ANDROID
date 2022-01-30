package im.koala.data.repository

import im.koala.data.repository.local.KeywordLocalDataSource
import im.koala.data.repository.remote.KeywordRemoteDataSource
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val keywordLocalDataSource: KeywordLocalDataSource,
    private val keywordRemoteDataSource: KeywordRemoteDataSource
) : KeywordRepository {
    override fun getSiteLocalizedMessage(site: Site): String {
        return keywordLocalDataSource.getSiteLocalizedMessage(site)
    }

    override suspend fun getKeywordNotices(
        keyword: String,
        search: String?,
        site: Site?
    ): List<KeywordNotice> {
        return if (search == null) {
            keywordRemoteDataSource.getKeywordNotices(keyword, site)
        } else {
            keywordRemoteDataSource.searchKeywordNotices(search, keyword, site)
        }
    }

    override suspend fun keepSelectedKeywordNotices(keywordNotices: List<KeywordNotice>) {

    }

    override suspend fun removeSelectedKeywordNotices(keywordNotices: List<KeywordNotice>) {
        keywordRemoteDataSource.removeKeywordNotices(keywordNotices)
    }
}