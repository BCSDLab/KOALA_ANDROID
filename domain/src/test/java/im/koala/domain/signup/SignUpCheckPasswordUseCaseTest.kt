package im.koala.domain.signup

import im.koala.domain.usecase.SignUpCheckPasswordUseCase
import im.koala.domain.util.checkpassword.PasswordChecker
import org.junit.Test

class SignUpCheckPasswordUseCaseTest {
    @Test
    fun checkPassword() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = ""),
                PasswordChecker.ERR_NO_INPUT,
                PasswordChecker.MASK_ERR_NO_INPUT
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "1234"),
                PasswordChecker.ERR_LENGTH_TOO_SHORT,
                PasswordChecker.MASK_ERR_LENGTH
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "01234567890123456789"),
                PasswordChecker.ERR_LENGTH_TOO_LONG,
                PasswordChecker.MASK_ERR_LENGTH
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "asdfasdfff"),
                PasswordChecker.ERR_NOT_CONTAINS_NUMBER,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_NUMBER
            ) && PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "asdfasdfff"),
                PasswordChecker.ERR_NOT_CONTAINS_SPECIAL_CHARACTER,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_SPECIAL_CHARACTER
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "1234567890"),
                PasswordChecker.ERR_NOT_CONTAINS_ENGLISH,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_ENGLISH
            ) && PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "1234567890"),
                PasswordChecker.ERR_NOT_CONTAINS_SPECIAL_CHARACTER,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_SPECIAL_CHARACTER
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "*&^&*&^&*&"),
                PasswordChecker.ERR_NOT_CONTAINS_ENGLISH,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_ENGLISH
            ) && PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "*&^&*&^&*&"),
                PasswordChecker.ERR_NOT_CONTAINS_NUMBER,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_NUMBER
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "asdf1234"),
                PasswordChecker.ERR_NOT_CONTAINS_SPECIAL_CHARACTER,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_SPECIAL_CHARACTER
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "asdf*^&^"),
                PasswordChecker.ERR_NOT_CONTAINS_NUMBER,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_NUMBER
            )
        )

        assert(
            PasswordChecker.contains(
                signUpCheckPasswordUseCase(password = "!!@@1234"),
                PasswordChecker.ERR_NOT_CONTAINS_ENGLISH,
                PasswordChecker.MASK_ERR_NOT_CONTAINS_ENGLISH
            )
        )

        assert(
            signUpCheckPasswordUseCase("qw12er#@ty") == PasswordChecker.NO_ERR
        )
    }
}