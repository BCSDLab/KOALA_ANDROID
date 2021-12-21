package im.koala.domain.util.checkpassword

import im.koala.domain.constants.PASSWORD_LENGTH_MAX
import im.koala.domain.constants.PASSWORD_LENGTH_MIN

object PasswordChecker {
    fun checkPassword(password: CharSequence): PasswordCheckResult {
        val result = checkPasswordNoneInput(password) +
            checkPasswordLength(password) +
            checkPasswordContainsNotSupportedCharacter(password) +
            checkPasswordNotContains(password)

        if(result == PasswordCheckStatus.OK) return PasswordCheckStatus.OK
        return result
    }

    private fun checkPasswordContainsNotSupportedCharacter(password: CharSequence) =
        if (PasswordRegexps.REGEX_MATCH_SUPPORTED_CHARACTERS.matchEntire(password) == null) PasswordCheckStatus.NotSupportCharactersError
        else PasswordCheckStatus.OK

    private fun checkPasswordLength(password: CharSequence) =
        when {
            password.length < PASSWORD_LENGTH_MIN -> PasswordCheckStatus.TooShortCharactersError
            password.length > PASSWORD_LENGTH_MAX -> PasswordCheckStatus.TooLongCharactersError
            else -> PasswordCheckStatus.OK
        }

    private fun checkPasswordNotContains(password: CharSequence): PasswordCheckResult {
        val noEnglish =
            if (PasswordRegexps.REGEX_CONTAINS_ENGLISH.matchEntire(password) == null) {
                PasswordCheckStatus.NotContainsEnglishError
            } else {
                PasswordCheckStatus.OK
            }
        val noDigit =
            if (PasswordRegexps.REGEX_CONTAINS_NUMBER.matchEntire(password) == null) {
                PasswordCheckStatus.NotContainsNumberError
            } else {
                PasswordCheckStatus.OK
            }
        val noSpecials =
            if (PasswordRegexps.REGEX_CONTAINS_SPECIAL_CHARACTER.matchEntire(password) == null) {
                PasswordCheckStatus.NotContainsSpecialCharacterError
            } else {
                PasswordCheckStatus.OK
            }

        return noEnglish + noDigit + noSpecials
    }

    private fun checkPasswordNoneInput(password: CharSequence) =
        if (password.isEmpty()) PasswordCheckStatus.NoSuchInputError
        else PasswordCheckStatus.OK

    /* e.g.
    0x10000010 -> No input
    0x11xxxxxx -> Contains not supported characters
    0x10000100 -> Not contains special characters
    0x10101100 -> Length is too short and not contains number and special characters
    0x10200100 -> Length is too long and not contains special characters
    */

    const val MASK_ERR_CONTAINS_NOT_SUPPORTED_CHARACTERS = 0x1f000000
    const val MASK_ERR_LENGTH = 0x10f00000
    const val MASK_ERR_NOT_CONTAINS_SPECIFIC_CHARACTERS = 0x100fff00
    const val MASK_ERR_NOT_CONTAINS_ENGLISH = 0x100f0000
    const val MASK_ERR_NOT_CONTAINS_NUMBER = 0x1000f000
    const val MASK_ERR_NOT_CONTAINS_SPECIAL_CHARACTER = 0x10000f00
    const val MASK_ERR_NO_INPUT = 0x100000f0

    const val ERR_CONTAINS_NOT_SUPPORTED_CHARACTERS = 0x11000000
    const val ERR_LENGTH_TOO_SHORT = 0x10100000
    const val ERR_LENGTH_TOO_LONG = 0x10200000
    const val ERR_NOT_CONTAINS_ENGLISH = 0x10010000
    const val ERR_NOT_CONTAINS_NUMBER = 0x10001000
    const val ERR_NOT_CONTAINS_SPECIAL_CHARACTER = 0x10000100
    const val ERR_NO_INPUT = 0x10000010

    const val NO_ERR = 0x00000000
}