package im.koala.domain.usecase

import im.koala.domain.state.NetworkState
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class SnsLoginUseCase @Inject constructor (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        snsType: String,
        accessToken: String,
        deviceToken: String,
    ): NetworkState {

        return userRepository.postSnsLogin(
            snsType = snsType,
            accessToken = accessToken,
            deviceToken = deviceToken,
        )
    }
}