package im.koala.data.source.remote

import im.koala.data.api.NoAuthApi
import im.koala.data.api.response.toErrorResponse
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_EMAIL
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_ID
import im.koala.data.constant.KOALA_API_ERROR_CODE_DUPLICATED_NICKNAME
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE
import im.koala.data.source.SignUpDataSource
import retrofit2.HttpException
import javax.inject.Inject

class SignUpRemoteDataSource @Inject constructor(
    private val noAuthApi: NoAuthApi
) : SignUpDataSource {
    override suspend fun checkIdIsAvailable(id: String): Boolean {
        return try {
            noAuthApi.checkAccount(id) != KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
        } catch (t: Throwable) {
            if(t is HttpException) {
                if(t.toErrorResponse().errorCode == KOALA_API_ERROR_CODE_DUPLICATED_ID) return true
                else throw t
            }
            else throw t
        }
    }

    override suspend fun checkNicknameIsAvailable(nickname: String): Boolean {
        return try {
            noAuthApi.checkAccount(nickname) == KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE
        } catch (t: Throwable) {
            if(t is HttpException) {
                if(t.toErrorResponse().errorCode == KOALA_API_ERROR_CODE_DUPLICATED_NICKNAME) return false
                else throw t
            }
            else throw t
        }
    }

    override suspend fun checkEmailIsAvailable(email: String): Boolean {
        return try {
            noAuthApi.checkEmail(email) == KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
        } catch (t: Throwable) {
            if(t is HttpException) {
                if(t.toErrorResponse().errorCode == KOALA_API_ERROR_CODE_DUPLICATED_EMAIL) return false
                else throw t
            }
            else throw t
        }
    }
}