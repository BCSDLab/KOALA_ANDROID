package im.koala.data.repository.remote

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site

interface KeywordRemoteDataSource {

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