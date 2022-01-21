package im.koala.data.source.remote

import im.koala.data.constant.ERROR_MESSAGE_NOT_USE_REMOTE
import im.koala.data.source.KeywordDataSource
import im.koala.domain.entity.keyword.KeywordListItem
import im.koala.domain.entity.keyword.Site
import javax.inject.Inject

class KeywordRemoteDataSource @Inject constructor(

) : KeywordDataSource {
    override fun getSiteLocalizedMessage(site: Site): String {
        throw IllegalAccessException(ERROR_MESSAGE_NOT_USE_REMOTE)
    }

    override fun getKeywordListItems(keyword: String): List<KeywordListItem> {
        return emptyList()
    }
}