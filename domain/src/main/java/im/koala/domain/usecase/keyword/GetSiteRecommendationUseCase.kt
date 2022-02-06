package im.koala.domain.usecase.keyword

import im.koala.domain.state.NetworkState
import im.koala.domain.repository.KeywordAddRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSiteRecommendationUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    operator fun invoke(): Flow<NetworkState> = flow {
        emit(NetworkState.Loading)
        var result = keywordAddRepository.getKeywordSiteRecommendation()
        emit(result)
    }
}