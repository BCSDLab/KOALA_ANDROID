package im.koala.data.repository

import im.koala.data.module.LocalDataSource
import im.koala.data.module.RemoteDataSource
import im.koala.data.source.KeywordDataSource
import im.koala.domain.entity.keyword.KeywordListItem
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

    override fun getKeywordItemList(keyword: String) : List<KeywordListItem>{
        return keywordRemoteDataSource.getKeywordListItems(keyword)
    }

    override fun keepSelectedKeywordItems(keywordDetailItems: List<KeywordListItem>) {

    }

    override fun removeSelectedKeywordItems(keywordDetailItems: List<KeywordListItem>) {

    }
}