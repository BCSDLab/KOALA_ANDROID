package im.koala.domain.usecase.user

import im.koala.domain.repository.UserRepository
import im.koala.domain.state.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithoutIdPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(deviceToken: String): Flow<Result> = flow {
        emit(userRepository.loginWithoutSignUp(deviceToken))
    }
}