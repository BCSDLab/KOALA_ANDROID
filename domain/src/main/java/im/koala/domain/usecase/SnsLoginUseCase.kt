package im.koala.domain.usecase

import im.koala.domain.model.CommonResponse
import im.koala.domain.model.TokenResponse
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class SnsLoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        snsType: String,
        accessToken: String,
        deviceToken: String,
        onSuccess: (TokenResponse) -> Unit,
        onFail: (CommonResponse) -> Unit
    ) {
        userRepository.postSnsLogin(
            snsType = snsType,
            accessToken = accessToken,
            deviceToken = deviceToken,
            onSuccess = { onSuccess(it) },
            onFail = { onFail(it) }
        )
    }
}