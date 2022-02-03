package im.koala.data.repository.remote

import im.koala.data.api.AuthApi
import im.koala.data.api.NoAuthApi
import im.koala.data.api.response.KeywordResponse
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.entity.KeywordBodyEntity
import im.koala.data.entity.TokenBodyEntity
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl@Inject constructor (private val noAuth: NoAuthApi, private val auth: AuthApi) : UserRemoteDataSource {

    override suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): Response<TokenBodyEntity> {
        return noAuth.postSnsLogin(snsType = snsType, accessToken = accessToken, deviceToken = deviceToken)
    }

    override suspend fun getKeyword(): Response<KeywordBodyEntity> {
        return auth.getKeyword()
    }

}