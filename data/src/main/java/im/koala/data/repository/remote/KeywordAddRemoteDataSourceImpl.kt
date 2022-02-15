package im.koala.data.repository.remote

import im.koala.data.api.AuthApi
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.api.response.keywordadd.KeywordAddResponse
import javax.inject.Inject
import retrofit2.Response

class KeywordAddRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : KeywordAddRemoteDataSource {
    override suspend fun pushKeyword(keywordAddResponse: KeywordAddResponse): Response<ResponseWrapper<String>> {
        return authApi.pushKeyword(keywordAddResponse)
    }

    override suspend fun deleteKeyword(keyword: String): Response<ResponseWrapper<String>> {
        return authApi.deleteKeyword(keyword)
    }

    override suspend fun getKeywordRecommendation(): Response<ResponseWrapper<List<String>>> {
        return authApi.getKeywordRecommendation()
    }

    override suspend fun getKeywordSiteRecommendation(): Response<ResponseWrapper<List<String>>> {
        return authApi.getKeywordSiteRecommendation()
    }

    override suspend fun getKeywordSiteSearch(site: String): Response<ResponseWrapper<List<String>>> {
        return authApi.getKeywordSiteSearch(site)
    }

    override suspend fun getKeywordSearch(keyword: String): Response<ResponseWrapper<List<String>>> {
        return authApi.getKeywordSearch(keyword)
    }
}