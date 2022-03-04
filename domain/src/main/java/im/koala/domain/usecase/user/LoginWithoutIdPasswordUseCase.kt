package im.koala.domain.usecase.user

import im.koala.domain.model.TokenResponse
import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithoutIdPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(deviceToken: String): Flow<Result<TokenResponse>> = flow {
        emit(userRepository.loginWithoutSignUp(deviceToken))
    }
}