package im.koala.domain.usecase

import im.koala.domain.repository.SignUpRepository
import im.koala.domain.util.checkemail.EmailCheckResult
import im.koala.domain.util.checkemail.EmailChecker
import javax.inject.Inject

class SignUpCheckEmailUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(email: String): EmailCheckResult =
        when {
            email.isBlank() -> EmailCheckResult.NoSuchInputError
            !EmailChecker.isEmail(email) -> EmailCheckResult.NotEmailFormatError
            signUpRepository.checkEmailDuplicate(email) -> EmailCheckResult.EmailDuplicatedError
            else -> EmailCheckResult.OK
        }
}