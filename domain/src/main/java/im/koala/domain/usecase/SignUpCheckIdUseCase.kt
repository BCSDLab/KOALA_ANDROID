package im.koala.domain.usecase

import im.koala.domain.repository.SignUpRepository

const val ID_OK = 0
const val ID_BLANK = 2
const val ID_DUPLICATED = 3

class SignUpCheckIdUseCase(
    private val signUpRepository: SignUpRepository
) {
    operator fun invoke(id: String): Int =
        when {
            id.isBlank() -> ID_BLANK
            signUpRepository.checkIdDuplicate(id) -> ID_DUPLICATED
            else -> ID_OK
        }
}