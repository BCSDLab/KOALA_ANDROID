package im.koala.data.repository.remote

import im.koala.data.entity.KeywordBodyEntity
import im.koala.data.entity.TokenBodyEntity
import retrofit2.Response
interface UserRemoteDataSource {
    suspend fun postSnsLogin(snsType: String, accessToken: String): Response<TokenBodyEntity>
    suspend fun getKeyword(): Response<KeywordBodyEntity>
}