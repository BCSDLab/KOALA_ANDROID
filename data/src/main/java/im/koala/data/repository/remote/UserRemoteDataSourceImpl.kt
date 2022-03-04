package im.koala.data.repository.remote

import im.koala.data.api.AuthApi
import im.koala.data.api.NoAuthApi
import im.koala.data.api.request.signup.SignUpRequest
import im.koala.data.api.request.user.UserRequest
import im.koala.data.api.response.toErrorResponse
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_EMAIL
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_ID
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_NICKNAME
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE
import im.koala.data.entity.KeywordBodyEntity
import im.koala.data.entity.TokenBodyEntity
import im.koala.data.entity.TokenEntity
import im.koala.data.mapper.signup.toSignUpResult
import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.util.toSHA256
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val noAuthApi: NoAuthApi,
    private val authApi: AuthApi
) : UserRemoteDataSource {

    override suspend fun postSnsLogin(
        snsType: String,
        accessToken: String,
        deviceToken: String
    ): Response<TokenBodyEntity> {
        return noAuthApi.postSnsLogin(
            snsType = snsType,
            accessToken = accessToken,
            deviceToken = deviceToken
        )
    }

    override suspend fun getKeyword(): Response<KeywordBodyEntity> {
        return authApi.getKeyword()
    }

    override suspend fun checkIdIsAvailable(id: String): Boolean {
        return try {
            noAuthApi.checkAccount(id).body != KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
        } catch (t: Throwable) {
            if (t is HttpException) {
                if (t.toErrorResponse().errorCode == KOALA_API_ERROR_CODE_DUPLICATED_ID) return true
                else throw t
            } else {
                throw t
            }
        }
    }

    override suspend fun checkNicknameIsAvailable(nickname: String): Boolean {
        return try {
            noAuthApi.checkNickname(nickname).body == KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE
        } catch (t: Throwable) {
            if (t is HttpException) {
                if (t.toErrorResponse().errorCode == KOALA_API_ERROR_CODE_DUPLICATED_NICKNAME) return false
                else throw t
            } else {
                throw t
            }
        }
    }

    override suspend fun checkEmailIsAvailable(email: String): Boolean {
        return try {
            noAuthApi.checkEmail(email).body == KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
        } catch (t: Throwable) {
            if (t is HttpException) {
                if (t.toErrorResponse().errorCode == KOALA_API_ERROR_CODE_DUPLICATED_EMAIL) return false
                else throw t
            } else {
                throw t
            }
        }
    }

    override suspend fun signUp(
        accountId: String,
        accountEmail: String,
        accountNickname: String,
        password: String
    ): SignUpResult {
        return try {
            val result = noAuthApi.signUp(
                SignUpRequest(
                    account = accountId,
                    email = accountEmail,
                    nickname = accountNickname,
                    password = password.toSHA256()
                )
            )
            result.body.toSignUpResult()
        } catch (t: Throwable) {
            val errorMessage =
                if (t is HttpException)
                    t.toErrorResponse().errorMessage
                else
                    t.message ?: ""
            SignUpResult.Failed(errorMessage)
        }
    }

    override suspend fun login(
        accountId: String,
        password: String,
        deviceToken: String
    ): TokenEntity {
        val tokenBodyEntity = noAuthApi.login(
            deviceToken = deviceToken,
            userRequest = UserRequest(
                accountId, password
            )
        )

        if (tokenBodyEntity.body == null)
            throw RuntimeException("Token body is null!")

        return TokenEntity(
            tokenBodyEntity.body.accessToken,
            tokenBodyEntity.body.refreshToken
        )
    }

    override suspend fun loginWithoutSignUp(deviceToken: String): TokenEntity {
        val tokenBodyEntity = noAuthApi.loginNonMember(deviceToken)

        if (tokenBodyEntity.body == null)
            throw RuntimeException("Token body is null!")

        return TokenEntity(
            tokenBodyEntity.body.accessToken,
            tokenBodyEntity.body.refreshToken
        )
    }
}