package im.koala.domain.repository

interface SignUpRepository {
    /**
     * @return true if id is duplicated, false if id is not duplicated
     */
    fun checkIdDuplicate(id: String): Boolean

    /**
     * @return true if email is duplicated, false if email is not duplicated
     */
    fun checkEmailDuplicate(email: String): Boolean

    /**
     * @return true if email is duplicated, false if email is not duplicated
     */
    fun checkNicknameDuplicate(nickname: String): Boolean

    fun signUp(
        id: String,
        password: String,
        email: String,
        nickname: String
    )
}