package im.koala.data.repository.remote

import im.koala.data.entity.TokenBodyEntity
import im.koala.domain.entity.signup.SignUpResult
import retrofit2.Response
interface UserRemoteDataSource {
    suspend fun postSnsLogin(snsType: String, accessToken: String): Response<TokenBodyEntity>
    suspend fun checkIdIsAvailable(id: String): Boolean
    suspend fun checkNicknameIsAvailable(nickname: String): Boolean
    suspend fun checkEmailIsAvailable(email: String): Boolean
    suspend fun signUp(
        accountId: String,
        accountEmail: String,
        accountNickname: String,
        password: String
    ): SignUpResult
}