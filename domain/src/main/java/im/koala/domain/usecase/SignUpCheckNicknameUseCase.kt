package im.koala.domain.usecase

import im.koala.domain.repository.SignUpRepository
import im.koala.domain.util.checknickname.NicknameCheckResult

class SignUpCheckNicknameUseCase(
    private val signUpRepository: SignUpRepository
) {
    operator fun invoke(nickname: String): NicknameCheckResult =
        when {
            nickname.isBlank() -> NicknameCheckResult.NoSuchInputError
            signUpRepository.checkNicknameDuplicate(nickname) -> NicknameCheckResult.NicknameDuplicatedError
            else -> NicknameCheckResult.OK
        }
}