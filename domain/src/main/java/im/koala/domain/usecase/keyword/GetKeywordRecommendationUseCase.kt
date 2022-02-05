package im.koala.domain.usecase.keyword

import im.koala.bcsd.state.NetworkState
import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetKeywordRecommendationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<NetworkState> = flow{
        emit(NetworkState.Loading)
        var result = userRepository.getKeywordRecommendation()
        emit(result)
    }
}