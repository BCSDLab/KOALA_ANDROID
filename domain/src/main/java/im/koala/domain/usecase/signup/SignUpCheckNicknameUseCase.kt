package im.koala.domain.usecase.signup

import im.koala.domain.repository.UserRepository
import im.koala.domain.util.checknickname.NicknameCheckResult
import javax.inject.Inject

class SignUpCheckNicknameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): NicknameCheckResult =
        when {
            nickname.isBlank() -> NicknameCheckResult.NoSuchInputError
            userRepository.checkNicknameDuplicate(nickname) -> NicknameCheckResult.NicknameDuplicatedError
            else -> NicknameCheckResult.OK
        }
}