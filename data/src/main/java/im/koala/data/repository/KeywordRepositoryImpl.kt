package im.koala.data.repository

import im.koala.data.module.LocalDataSource
import im.koala.data.module.RemoteDataSource
import im.koala.data.source.KeywordDataSource
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    @LocalDataSource private val keywordLocalDataSource: KeywordDataSource,
    @RemoteDataSource private val keywordRemoteDataSource: KeywordDataSource
) : KeywordRepository {
    override fun getSiteLocalizedMessage(site: Site): String {
        return keywordLocalDataSource.getSiteLocalizedMessage(site)
    }

    override fun getKeywordNotices(
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

    override fun keepSelectedKeywordNotices(keywordNotices: List<KeywordNotice>) {

    }

    override fun removeSelectedKeywordNotices(keywordNotices: List<KeywordNotice>) {
        keywordRemoteDataSource.removeKeywordNotices(keywordNotices)
    }
}