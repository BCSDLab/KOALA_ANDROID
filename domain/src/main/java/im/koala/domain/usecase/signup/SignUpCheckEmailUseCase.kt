package im.koala.domain.usecase.signup

import im.koala.domain.repository.UserRepository
import im.koala.domain.util.checkemail.EmailCheckResult
import im.koala.domain.util.checkemail.EmailChecker
import javax.inject.Inject

class SignUpCheckEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String): EmailCheckResult =
        when {
            email.isBlank() -> EmailCheckResult.NoSuchInputError
            !EmailChecker.isEmail(email) -> EmailCheckResult.NotEmailFormatError
            userRepository.checkEmailDuplicate(email) -> EmailCheckResult.EmailDuplicatedError
            else -> EmailCheckResult.OK
        }
}