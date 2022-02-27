package im.koala.domain.usecase.user

import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class GetAutoLoginStateUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.isAutoLogin()
}