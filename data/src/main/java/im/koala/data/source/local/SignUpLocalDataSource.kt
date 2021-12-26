package im.koala.data.source.local

import im.koala.data.source.SignUpDataSource
import im.koala.domain.entity.signup.SignUpResult

class SignUpLocalDataSource : SignUpDataSource {
    override suspend fun checkIdIsAvailable(id: String): Boolean {
        return false
    }

    override suspend fun checkNicknameIsAvailable(nickname: String): Boolean {
        return false
    }

    override suspend fun checkEmailIsAvailable(email: String): Boolean {
        return false
    }

    override suspend fun signUp(
        accountId: String,
        accountEmail: String,
        accountNickname: String,
        password: String
    ): SignUpResult {
        throw IllegalAccessException("Not supported in local source")
    }
}