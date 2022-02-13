package im.koala.domain.usecase

import im.koala.bcsd.state.Result
import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SnsLoginUseCase @Inject constructor (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        snsType: String,
        accessToken: String,
        deviceToken: String,
    ): Flow<Result> = flow {
        emit(Result.Loading)
        var result = userRepository.postSnsLogin(
            snsType = snsType,
            accessToken = accessToken,
            deviceToken = deviceToken,
        )
        emit(result)
    }
}