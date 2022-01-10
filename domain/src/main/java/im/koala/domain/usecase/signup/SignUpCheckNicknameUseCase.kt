package im.koala.domain.usecase.signup

import im.koala.domain.repository.SignUpRepository
import im.koala.domain.util.checknickname.NicknameCheckResult
import javax.inject.Inject

class SignUpCheckNicknameUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(nickname: String): NicknameCheckResult =
        when {
            nickname.isBlank() -> NicknameCheckResult.NoSuchInputError
            signUpRepository.checkNicknameDuplicate(nickname) -> NicknameCheckResult.NicknameDuplicatedError
            else -> NicknameCheckResult.OK
        }
}