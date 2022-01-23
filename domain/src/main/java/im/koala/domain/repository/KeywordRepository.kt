package im.koala.domain.repository

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site

interface KeywordRepository {
    fun getKeywordNotices(
        keyword: String
    ): List<KeywordNotice>

    fun getSiteLocalizedMessage(
        site: Site
    ): String

    fun keepSelectedKeywordNotices(
        keywordDetailItems: List<KeywordNotice>
    )

    fun removeSelectedKeywordNotices(
        keywordDetailItems: List<KeywordNotice>
    )
}