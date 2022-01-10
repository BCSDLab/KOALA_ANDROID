package im.koala.domain.usecase.signup

import im.koala.domain.util.checkpassword.PasswordCheckResult
import im.koala.domain.util.checkpassword.PasswordChecker
import javax.inject.Inject

/**
 * @see im.koala.domain.util.checkpassword.PasswordChecker.Companion
 */
class SignUpCheckPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): PasswordCheckResult =
        PasswordChecker.checkPassword(password)
}