package im.koala.domain.entity.signup

sealed class SignUpResult {

    data class OK(
        val userId: Int,
        val accountId: String,
        val accountEmail: String,
        val accountNickname: String,
        val userType: Int,
        val isAuth: Int
    ) : SignUpResult() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as OK

            if (userId != other.userId) return false
            if (accountId != other.accountId) return false
            if (accountEmail != other.accountEmail) return false
            if (accountNickname != other.accountNickname) return false
            if (userType != other.userType) return false
            if (isAuth != other.isAuth) return false

            return true
        }

        override fun hashCode(): Int {
            var result = userId
            result = 31 * result + accountId.hashCode()
            result = 31 * result + accountEmail.hashCode()
            result = 31 * result + accountNickname.hashCode()
            result = 31 * result + userType
            result = 31 * result + isAuth
            return result
        }

        override fun toString(): String {
            return "OK(userId=$userId, accountId='$accountId', accountEmail='$accountEmail', accountNickname='$accountNickname', userType=$userType, isAuth=$isAuth)"
        }
    }

    class Failed(val errorMessage: String) : SignUpResult() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Failed

            if (errorMessage != other.errorMessage) return false

            return true
        }

        override fun hashCode(): Int {
            return errorMessage.hashCode()
        }

        override fun toString(): String {
            return "Failed(errorMessage='$errorMessage')"
        }
    }
}