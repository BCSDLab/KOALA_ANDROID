package im.koala.domain.usecase

import im.koala.domain.repository.SignUpRepository
import im.koala.domain.util.checkid.IdCheckResult
import javax.inject.Inject

class SignUpCheckIdUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    operator fun invoke(id: String): IdCheckResult =
        when {
            id.isBlank() -> IdCheckResult.NoSuchInputError
            signUpRepository.checkIdDuplicate(id) -> IdCheckResult.IdDuplicatedError
            else -> IdCheckResult.OK
        }
}