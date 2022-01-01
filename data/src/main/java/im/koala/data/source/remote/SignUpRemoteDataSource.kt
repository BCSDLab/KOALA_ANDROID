package im.koala.data.source.remote

import im.koala.data.api.NoAuthApi
import im.koala.data.api.request.signup.SignUpRequest
import im.koala.data.api.response.toErrorResponse
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_EMAIL
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_ID
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_NICKNAME
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE
import im.koala.data.mapper.signup.toSignUpResult
import im.koala.data.source.SignUpDataSource
import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.util.toSHA256
import retrofit2.HttpException
import javax.inject.Inject

class SignUpRemoteDataSource @Inject constructor(
    private val noAuthApi: NoAuthApi
) : SignUpDataSource {

    override suspend fun checkIdIsAvailable(id: String): Boolean {
        return try {
            noAuthApi.checkAccount(id).body != KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
        } catch (t: Throwable) {
            if (t is HttpException) {
                if (t.toErrorResponse().errorCode == KOALA_API_ERROR_CODE_DUPLICATED_ID) return true
                else throw t
            } else{
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
            } else{
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
}