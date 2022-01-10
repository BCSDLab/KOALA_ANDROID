package im.koala.data.source

import im.koala.domain.entity.signup.SignUpResult

interface SignUpDataSource {
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