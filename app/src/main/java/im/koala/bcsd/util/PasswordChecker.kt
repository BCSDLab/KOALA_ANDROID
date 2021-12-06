package im.koala.bcsd.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import im.koala.bcsd.R

object PasswordChecker {
    /*
    Password result
    0x00000000 -> OK
    0x10000000 -> Password is less than 7 characters
    0x20000000 -> Password is more than 16 characters
    0x30000000 -> Password have not supported characters
    0x4xyz0000 -> Password is not have [x: english] [y: Number] [z: special] characters.
    */

    const val PASSWORD_OK = 0x00000000
    const val PASSWORD_LESS_THAN_7_CHARACTERS = 0x10000000
    const val PASSWORD_MORE_THAN_16_CHARACTERS = 0x20000000
    const val PASSWORD_NOT_SUPPORTED_CHARACTERS = 0x30000000
    const val PASSWORD_NOT_HAVE_SOME_CHARACTERS = 0x40000000
    const val PASSWORD_ENGLISH = 0x01000000
    const val PASSWORD_NUMBER = 0x00100000
    const val PASSWORD_SPECIAL = 0x00010000

    fun checkPassword(password: String): Int {
        return when {
            password.length < 8 -> PASSWORD_LESS_THAN_7_CHARACTERS
            password.length > 15 -> PASSWORD_MORE_THAN_16_CHARACTERS
            //TODO Branch not supported characters
            else -> {
                var isError = false
                var errorCode = PASSWORD_NOT_HAVE_SOME_CHARACTERS

                if (!password.contains(Regex("[a-zA-Z]"))) {
                    isError = true
                    errorCode += PASSWORD_ENGLISH
                }
                if (!password.contains(Regex("[0-9]"))) {
                    isError = true
                    errorCode += PASSWORD_NUMBER
                }

                return if (isError) errorCode else PASSWORD_OK
            }
        }
    }

    @Composable
    fun PasswordErrorString(passwordErrorCode: Int) : String?{
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
    }
}