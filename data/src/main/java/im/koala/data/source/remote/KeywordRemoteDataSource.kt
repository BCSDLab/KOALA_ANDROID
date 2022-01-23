package im.koala.data.source.remote

import im.koala.data.api.AuthApi
import im.koala.data.constant.ERROR_MESSAGE_NOT_USE_REMOTE
import im.koala.data.mapper.keyword.toKeywordNotice
import im.koala.data.source.KeywordDataSource
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import javax.inject.Inject

class KeywordRemoteDataSource @Inject constructor(
    private val authApi: AuthApi
) : KeywordDataSource {
    override fun getSiteLocalizedMessage(site: Site): String {
        throw IllegalAccessException(ERROR_MESSAGE_NOT_USE_REMOTE)
    }

    override fun getKeywordNotices(keyword: String, site: Site?): List<KeywordNotice> {
        return authApi.getKeywordList(
            keywordName = keyword,
            site = site.toString()
        ).map {
            it.toKeywordNotice()
        }
    }

    override fun searchKeywordNotices(search: String, keyword: String, site: Site?): List<KeywordNotice> {
        return authApi.searchKeywordList(
            search = search,
            keywordName = keyword,
            site = site.toString()
        ).map {
            it.toKeywordNotice()
        }
    }

    override fun removeKeywordNotices(keywordNotices: List<KeywordNotice>) {
        authApi.removeKeywordNotice(keywordNotices.map { it.id })
    }
}