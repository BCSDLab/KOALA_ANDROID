package im.koala.domain.util.checkpassword

import im.koala.domain.util.checkpassword.PasswordChecker.NO_ERR

open class PasswordCheckResult(val errorCode: Int) {
    override operator fun equals(other: Any?): Boolean {
        if (other is PasswordCheckResult) return this.errorCode == other.errorCode
        return false
    }

    override fun hashCode(): Int {
        return errorCode.hashCode()
    }

    operator fun plus(other: PasswordCheckResult): PasswordCheckResult =
        PasswordCheckResult(this.errorCode or other.errorCode)

    operator fun contains(passwordCheckResult: PasswordCheckResult): Boolean {
        return this.errorCode and passwordCheckResult.errorCode == passwordCheckResult.errorCode
    }

    operator fun get(passwordCheckResult: PasswordCheckResult): PasswordCheckResult? {
        return if (contains(passwordCheckResult)) passwordCheckResult else null
    }

    fun isOK() = this == PasswordCheckStatus.OK
    fun isError() = this != PasswordCheckStatus.OK
}

sealed class PasswordCheckStatus(errorCode: Int) : PasswordCheckResult(errorCode) {
    object OK : PasswordCheckStatus(NO_ERR)
    object NoSuchInputError : PasswordCheckStatus(PasswordChecker.ERR_NO_INPUT)
    object TooShortCharactersError : PasswordCheckStatus(PasswordChecker.ERR_LENGTH_TOO_SHORT)
    object TooLongCharactersError : PasswordCheckStatus(PasswordChecker.ERR_LENGTH_TOO_LONG)
    object NotContainsEnglishError : PasswordCheckStatus(PasswordChecker.ERR_NOT_CONTAINS_ENGLISH)
    object NotContainsNumberError : PasswordCheckStatus(PasswordChecker.ERR_NOT_CONTAINS_NUMBER)
    object NotContainsSpecialCharacterError :
        PasswordCheckStatus(PasswordChecker.ERR_NOT_CONTAINS_SPECIAL_CHARACTER)

    object NotSupportCharactersError :
        PasswordCheckStatus(PasswordChecker.ERR_CONTAINS_NOT_SUPPORTED_CHARACTERS)
}