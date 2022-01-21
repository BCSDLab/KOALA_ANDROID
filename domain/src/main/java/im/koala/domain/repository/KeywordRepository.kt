package im.koala.domain.repository

import im.koala.domain.entity.keyword.KeywordListItem
import im.koala.domain.entity.keyword.Site

interface KeywordRepository {
    fun getKeywordItemList(keyword: String): List<KeywordListItem>
    fun getSiteLocalizedMessage(site: Site): String
    fun keepSelectedKeywordItems(keywordDetailItems: List<KeywordListItem>)
    fun removeSelectedKeywordItems(keywordDetailItems: List<KeywordListItem>)
}