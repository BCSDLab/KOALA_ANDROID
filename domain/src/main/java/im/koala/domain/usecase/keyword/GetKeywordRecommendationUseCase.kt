package im.koala.domain.usecase.keyword

import im.koala.domain.state.NetworkState
import im.koala.domain.repository.KeywordAddRepository
import javax.inject.Inject

class GetKeywordRecommendationUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(): NetworkState = keywordAddRepository.getKeywordRecommendation()
}