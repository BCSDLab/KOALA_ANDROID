package im.koala.domain.usecase

import im.koala.bcsd.state.NetworkState
import im.koala.domain.constants.KAKAO
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        deviceToken: String,
    ): NetworkState {

        return userRepository.postSnsLogin(
            snsType = KAKAO,
            accessToken = accessToken,
            deviceToken = deviceToken,
        )
    }
}