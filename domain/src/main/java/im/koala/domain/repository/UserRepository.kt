package im.koala.domain.repository

import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.TokenResponse

interface UserRepository {
    suspend fun postSnsLogin(snsType: String, accessToken: String, onSuccess: (TokenResponse) -> Unit, onFail: (CommonResponse) -> Unit)

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
}