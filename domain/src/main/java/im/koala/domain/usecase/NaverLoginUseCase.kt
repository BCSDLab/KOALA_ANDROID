package im.koala.domain.usecase

import im.koala.domain.constants.NAVER
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.TokenResponse
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class NaverLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        onSuccess: (TokenResponse) -> Unit,
        onFail: (CommonResponse) -> Unit
    ) {
        userRepository.postSnsLogin(
            snsType = NAVER,
            accessToken = accessToken,
            onSuccess = { onSuccess(it) },
            onFail = { onFail(it) }
        )
    }
}