package im.koala.data.repository.remote

import im.koala.data.api.response.KeywordResponse
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.entity.KeywordBodyEntity
import im.koala.data.entity.TokenBodyEntity
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getKeyword(): Response<KeywordBodyEntity>
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): Response<TokenBodyEntity>
}