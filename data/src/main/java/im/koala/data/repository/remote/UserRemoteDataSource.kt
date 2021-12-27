package im.koala.data.repository.remote

import im.koala.data.entity.TokenBodyEntity
import im.koala.data.entity.TokenEntity
import retrofit2.Response
interface UserRemoteDataSource {
    suspend fun postSnsLogin(snsType: String, accessToken: String): Response<TokenBodyEntity>
}