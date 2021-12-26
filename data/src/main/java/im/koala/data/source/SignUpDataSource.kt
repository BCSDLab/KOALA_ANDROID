package im.koala.data.source

interface SignUpDataSource {
    suspend fun checkIdIsAvailable(id: String): Boolean
    suspend fun checkNicknameIsAvailable(nickname: String): Boolean
    suspend fun checkEmailIsAvailable(email: String): Boolean
}