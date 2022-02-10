package im.koala.data.repository.remote

import im.koala.data.api.AuthApi
import im.koala.data.api.NoAuthApi
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.entity.KeywordBodyEntity
import im.koala.data.entity.TokenBodyEntity
import im.koala.domain.model.KeywordAddResponse
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl@Inject constructor (private val noAuth: NoAuthApi, private val auth: AuthApi) : UserRemoteDataSource {

    override suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): Response<TokenBodyEntity> {
        return noAuth.postSnsLogin(snsType = snsType, accessToken = accessToken, deviceToken = deviceToken)
    }

    override suspend fun pushKeyword(keywordAddResponse: KeywordAddResponse): Response<ResponseWrapper<String>> {
        return auth.pushKeyword(keywordAddResponse)
    }

    override suspend fun deleteKeyword(keyword: String): Response<ResponseWrapper<String>> {
        return auth.deleteKeyword(keyword)
    }

    override suspend fun getKeywordRecommendation(): Response<ResponseWrapper<List<String>>> {
        return auth.getKeywordRecommendation()
    }

    override suspend fun getKeywordSiteRecommendation(): Response<ResponseWrapper<List<String>>> {
        return auth.getKeywordSiteRecommendation()
    }

    override suspend fun getKeywordSiteSearch(site: String): Response<ResponseWrapper<List<String>>> {
        return auth.getKeywordSiteSearch(site)
    }

    override suspend fun getKeywordSearch(keyword: String): Response<ResponseWrapper<List<String>>>{
        return auth.getKeywordSearch(keyword)
    }

    override suspend fun getKeyword(): Response<KeywordBodyEntity> {
        return auth.getKeyword()
    }

}