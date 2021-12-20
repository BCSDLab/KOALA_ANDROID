package im.koala.domain.usecase

import im.koala.domain.util.checkpassword.PasswordCheckResult
import im.koala.domain.util.checkpassword.PasswordChecker

/**
 * @see im.koala.domain.util.checkpassword.PasswordChecker.Companion
 */
class SignUpCheckPasswordUseCase {
    operator fun invoke(password: String): PasswordCheckResult =
        PasswordChecker.checkPassword(password)
}