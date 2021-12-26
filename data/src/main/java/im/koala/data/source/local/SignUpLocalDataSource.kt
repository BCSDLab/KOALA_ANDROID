package im.koala.data.source.local

import im.koala.data.source.SignUpDataSource

class SignUpLocalDataSource: SignUpDataSource {
    override suspend fun checkIdIsAvailable(id: String): Boolean {
        return false
    }

    override suspend fun checkNicknameIsAvailable(nickname: String): Boolean {
        return false
    }

    override suspend fun checkEmailIsAvailable(email: String): Boolean {
        return false
    }
}