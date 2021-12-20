package im.koala.domain.util.checkpassword

sealed class PasswordCheckResult(val errorCode: Int) {
    object OK : PasswordCheckResult(PasswordChecker.NO_ERR)
    object NoSuchInputError : PasswordCheckResult(PasswordChecker.ERR_NO_INPUT)
    object TooShortCharactersError : PasswordCheckResult(PasswordChecker.ERR_LENGTH_TOO_SHORT)
    object TooLongCharactersError : PasswordCheckResult(PasswordChecker.ERR_LENGTH_TOO_LONG)
    object NotContainsEnglishError : PasswordCheckResult(PasswordChecker.ERR_NOT_CONTAINS_ENGLISH)
    object NotContainsNumberError : PasswordCheckResult(PasswordChecker.ERR_NOT_CONTAINS_NUMBER)
    object NotContainsSpecialCharacterError :
        PasswordCheckResult(PasswordChecker.ERR_NOT_CONTAINS_SPECIAL_CHARACTER)

    object NotSupportCharactersError : PasswordCheckResult(PasswordChecker.ERR_CONTAINS_NOT_SUPPORTED_CHARACTERS)

    class MultipleError(errorCode: Int) : PasswordCheckResult(errorCode)

    operator fun plus(other: PasswordCheckResult): PasswordCheckResult =
        MultipleError(this.errorCode or other.errorCode)

    operator fun contains(passwordCheckResult: PasswordCheckResult) =
        if (passwordCheckResult == OK) {
            this.errorCode == OK.errorCode
        } else {
            this.errorCode and passwordCheckResult.errorCode == passwordCheckResult.errorCode
        }

    operator fun get(passwordCheckResult: PasswordCheckResult): PasswordCheckResult? {
        return if (contains(passwordCheckResult)) passwordCheckResult else null
    }

}