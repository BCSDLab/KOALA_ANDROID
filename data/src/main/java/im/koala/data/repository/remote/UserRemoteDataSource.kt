package im.koala.data.repository.remote

import im.koala.data.entity.KeywordBodyEntity
import im.koala.data.entity.TokenBodyEntity
import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.model.TokenResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun getKeyword(): Response<KeywordBodyEntity>
    suspend fun postSnsLogin(
        snsType: String,
        accessToken: String,
        deviceToken: String
    ): Response<TokenBodyEntity>

    suspend fun checkIdIsAvailable(id: String): Boolean
    suspend fun checkNicknameIsAvailable(nickname: String): Boolean
    suspend fun checkEmailIsAvailable(email: String): Boolean
    suspend fun signUp(
        accountId: String,
        accountEmail: String,
        accountNickname: String,
        password: String
    ): SignUpResult

    suspend fun login(
        accountId: String,
        password: String,
        deviceToken: String
    ): Response<TokenResponse>

    suspend fun loginWithoutSignUp(
        deviceToken: String
    ): Response<TokenResponse>
}