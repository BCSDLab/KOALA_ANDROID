package im.koala.domain.usecase.user

import im.koala.domain.repository.UserRepository
import im.koala.domain.state.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginWithIdPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(
        deviceToken: String,
        id: String,
        password: String
    ): Flow<Result> = flow {
        emit(Result.Loading)
        emit(userRepository.login(deviceToken, id, password))
    }
}