package im.koala.domain.repository

interface SignUpRepository {
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
        id: String,
        password: String,
        email: String,
        nickname: String
    ): Boolean
}