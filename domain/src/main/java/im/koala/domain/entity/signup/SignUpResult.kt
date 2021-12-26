package im.koala.domain.entity.signup

import java.io.Serializable

sealed class SignUpResult: Serializable {
    data class OK(
        val userId: Int,
        val accountId: String,
        val accountEmail: String,
        val accountNickname: String,
        val userType: Int,
        val isAuth: Int
    ): SignUpResult()
    class Failed(val errorMessage: String): SignUpResult()
}
