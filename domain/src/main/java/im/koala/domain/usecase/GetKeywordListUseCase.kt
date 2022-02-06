package im.koala.domain.usecase

import im.koala.domain.state.NetworkState
import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetKeywordListUseCase@Inject constructor (
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<NetworkState> = flow {
        emit(NetworkState.Loading)
        var result = userRepository.getKeyword()
        emit(result)
    }
}