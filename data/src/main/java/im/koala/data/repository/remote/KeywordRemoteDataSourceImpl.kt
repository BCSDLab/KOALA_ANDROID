package im.koala.data.repository.remote

import im.koala.data.api.AuthApi
import im.koala.data.mapper.keyword.toKeywordNotice
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import javax.inject.Inject

class KeywordRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : KeywordRemoteDataSource {

    override suspend fun getKeywordNotices(keyword: String, site: Site?): List<KeywordNotice> {
        return authApi.getKeywordList(
            keywordName = keyword,
            site = if(site == Site.All) null else site.toString()
        ).map {
            it.toKeywordNotice()
        }
    }

    override suspend fun searchKeywordNotices(
        search: String,
        keyword: String,
        site: Site?
    ): List<KeywordNotice> {
        return authApi.searchKeywordList(
            search = search,
            keywordName = keyword,
            site = site.toString()
        ).map {
            it.toKeywordNotice()
        }
    }

    override suspend fun removeKeywordNotices(keywordNotices: List<KeywordNotice>) {
        authApi.removeKeywordNotice(keywordNotices.map { it.id })
    }
}