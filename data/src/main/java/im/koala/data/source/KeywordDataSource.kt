package im.koala.data.source

import im.koala.domain.entity.keyword.KeywordListItem
import im.koala.domain.entity.keyword.Site

interface KeywordDataSource {
    fun getSiteLocalizedMessage(site: Site): String
    fun getKeywordListItems(keyword: String): List<KeywordListItem>
}