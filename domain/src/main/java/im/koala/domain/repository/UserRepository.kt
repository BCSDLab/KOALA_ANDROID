package im.koala.domain.repository

import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.state.Result

interface UserRepository {
    suspend fun getKeyword(): Result
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): Result
    fun getFCMToken(success: (String) -> Unit, fail: (String?) -> Unit)
    suspend fun login(
        deviceToken: String,
        id: String,
        password: String
    ): Result
    suspend fun loginWithoutSignUp(deviceToken: String): Result

    /**
     * @return true if id is duplicated, false if id is not duplicated
     */
    suspend fun checkIdDuplicate(id: String): Boolean

    /**
     * @return true if email is duplicated, false if email is not duplicated
     */
    suspend fun checkEmailDuplicate(email: String): Boolean

    /**
     * @return true if email is duplicated, false if email is not duplicated
     */
    suspend fun checkNicknameDuplicate(nickname: String): Boolean

    suspend fun signUp(
        accountId: String,
        password: String,
        accountEmail: String,
        accountNickname: String
    ): SignUpResult

    fun setAutoLogin(autoLogin: Boolean)
    fun isAutoLogin(): Boolean

    suspend fun getWebSocketToken(): kotlin.Result<String>
}