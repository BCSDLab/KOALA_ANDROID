package im.koala.data.source

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site

interface KeywordDataSource {
    fun getSiteLocalizedMessage(
        site: Site
    ): String

    fun getKeywordNotices(
        keyword: String,
        site: Site? = null
    ): List<KeywordNotice>

    fun searchKeywordNotices(
        search: String,
        keyword: String,
        site: Site? = null
    ): List<KeywordNotice>

    fun removeKeywordNotices(
        keywordNotices: List<KeywordNotice>
    )
}