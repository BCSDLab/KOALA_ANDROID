package im.koala.domain.usecase.signup

import im.koala.domain.repository.UserRepository
import im.koala.domain.util.checkid.IdCheckResult
import javax.inject.Inject

class SignUpCheckIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(id: String): IdCheckResult =
        when {
            id.isBlank() -> IdCheckResult.NoSuchInputError
            userRepository.checkIdDuplicate(id) -> IdCheckResult.IdDuplicatedError
            else -> IdCheckResult.OK
        }
}