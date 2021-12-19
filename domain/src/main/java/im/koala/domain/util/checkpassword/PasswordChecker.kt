package im.koala.domain.util.checkpassword

object PasswordChecker {
    fun checkPassword(password: CharSequence): Int {
        return checkPasswordContainsNotSupportedCharacter(password) or
            checkPasswordLength(password) or
            checkPasswordContainsEnglish(password) or
            checkPasswordContainsNumber(password) or
            checkPasswordContainsSymbol(password) or
            checkPasswordNoneInput(password)
    }

    private fun checkPasswordContainsNotSupportedCharacter(password: CharSequence) =
        if (PasswordRegexps.REGEX_MATCH_SUPPORTED_CHARACTERS.matchEntire(password) == null) ERR_CONTAINS_NOT_SUPPORTED_CHARACTERS
        else NO_ERR

    private fun checkPasswordLength(password: CharSequence) =
        when {
            password.length < PASSWORD_LENGTH_MIN -> ERR_LENGTH_TOO_SHORT
            password.length > PASSWORD_LENGTH_MAX -> ERR_LENGTH_TOO_LONG
            else -> NO_ERR
        }

    private fun checkPasswordContainsEnglish(password: CharSequence) =
        if (PasswordRegexps.REGEX_CONTAINS_ENGLISH.matchEntire(password) == null) ERR_NOT_CONTAINS_ENGLISH
        else NO_ERR

    private fun checkPasswordContainsNumber(password: CharSequence) =
        if (PasswordRegexps.REGEX_CONTAINS_NUMBER.matchEntire(password) == null) ERR_NOT_CONTAINS_NUMBER
        else NO_ERR

    private fun checkPasswordContainsSymbol(password: CharSequence) =
        if (PasswordRegexps.REGEX_CONTAINS_SPECIAL_CHARACTER.matchEntire(password) == null) ERR_NOT_CONTAINS_SPECIAL_CHARACTER
        else NO_ERR

    private fun checkPasswordNoneInput(password: CharSequence) =
        if (password.isEmpty()) ERR_NO_INPUT
        else NO_ERR

    /*@Composable
    fun PasswordErrorString(passwordErrorCode: Int): String? {
        return when {
            passwordErrorCode == PASSWORD_LESS_THAN_7_CHARACTERS -> {
                stringResource(R.string.signup_password_error_length_lower_8)
            }
            passwordErrorCode == PASSWORD_MORE_THAN_16_CHARACTERS -> {
                stringResource(R.string.signup_password_error_length_upper_15)
            }
            passwordErrorCode and 0xf0000000.toInt() == PASSWORD_NOT_HAVE_SOME_CHARACTERS -> {
                val notContains = mutableListOf<String>()

                if (passwordErrorCode and 0x0f000000 == PASSWORD_ENGLISH) {
                    notContains.add(stringResource(R.string.english))
                }
                if (passwordErrorCode and 0x00f00000 == PASSWORD_NUMBER) {
                    notContains.add(stringResource(R.string.number))
                }
                if (passwordErrorCode and 0x000f0000 == PASSWORD_SPECIAL) {
                    notContains.add(stringResource(R.string.special))
                }

                stringResource(
                    R.string.signup_password_error_not_contains,
                    notContains.joinToString()
                )
            }
            else -> null
        }
    }*/

    const val PASSWORD_LENGTH_MIN = 8
    const val PASSWORD_LENGTH_MAX = 15

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

    const val NO_ERR = 0x10000000

    fun contains(errorCode: Int, containsErrorCode: Int, mask: Int): Boolean {
        return errorCode and mask == containsErrorCode
    }

    /* e.g.
    0x10000010 -> No input
    0x11xxxxxx -> Contains not supported characters
    0x10000100 -> Not contains special characters
    0x10101100 -> Length is too short and not contains number and special characters
    0x10200100 -> Length is too long and not contains special characters
    */

}