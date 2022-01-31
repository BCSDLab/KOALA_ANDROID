package im.koala.data.repository.remote

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site

interface KeywordRemoteDataSource {

    suspend fun getKeywordNotices(
        keyword: String,
        site: Site? = null
    ): List<KeywordNotice>

    suspend fun searchKeywordNotices(
        search: String,
        keyword: String,
        site: Site? = null
    ): List<KeywordNotice>

    suspend fun removeKeywordNotices(
        keywordNotices: List<KeywordNotice>
    )

    suspend fun markAsReadKeywordNotice(
        keywordNotice: KeywordNotice
    )
}