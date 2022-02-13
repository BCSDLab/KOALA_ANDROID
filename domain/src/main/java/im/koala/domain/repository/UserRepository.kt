package im.koala.domain.repository

import im.koala.bcsd.state.NetworkState
import im.koala.domain.entity.signup.SignUpResult

interface UserRepository {
    suspend fun getKeyword(): NetworkState
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): NetworkState

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