package im.koala.domain.usecase

import im.koala.domain.repository.SignUpRepository

const val NICKNAME_OK = 0
const val NICKNAME_BLANK = 2
const val NICKNAME_DUPLICATED = 3

class SignUpCheckNicknameUseCase(
    private val signUpRepository: SignUpRepository
) {
    operator fun invoke(nickname: String): Int =
        when {
            nickname.isBlank() -> NICKNAME_BLANK
            signUpRepository.checkNicknameDuplicate(nickname) -> NICKNAME_DUPLICATED
            else -> NICKNAME_OK
        }
}