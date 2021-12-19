package im.koala.domain.usecase

import im.koala.domain.repository.SignUpRepository
import im.koala.domain.util.checkemail.EmailChecker

const val EMAIL_OK = 0
const val EMAIL_BLANK = 2
const val EMAIL_IS_NOT_EMAIL = 2
const val EMAIL_DUPLICATED = 3

class SignUpCheckEmailUseCase(
    private val signUpRepository: SignUpRepository
) {
    operator fun invoke(email: String): Int =
        when {
            email.isBlank() -> EMAIL_BLANK
            EmailChecker.isEmail(email) -> EMAIL_IS_NOT_EMAIL
            signUpRepository.checkEmailDuplicate(email) -> EMAIL_DUPLICATED
            else -> EMAIL_OK
        }
}