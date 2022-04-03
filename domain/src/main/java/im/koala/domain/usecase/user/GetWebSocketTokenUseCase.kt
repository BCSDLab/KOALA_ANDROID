package im.koala.domain.usecase.user

import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWebSocketTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() : Flow<String> = flow {
        try {
            emit(userRepository.getWebSocketToken().getOrThrow())
        } catch (e: Exception) {
            error(e)
        }
    }
}