package im.koala.domain.repository

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site

interface KeywordRepository {
    fun getKeywordNotices(
        keyword: String,
        search: String? = null,
        site: Site? = null
    ): List<KeywordNotice>

    fun getSiteLocalizedMessage(
        site: Site
    ): String

    fun keepSelectedKeywordNotices(
        keywordNotices: List<KeywordNotice>
    )

    fun removeSelectedKeywordNotices(
        keywordNotices: List<KeywordNotice>
    )
}