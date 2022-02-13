package im.koala.domain.usecase.keyword

import im.koala.domain.state.NetworkState
import im.koala.domain.repository.KeywordAddRepository
import javax.inject.Inject

class GetSiteRecommendationUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(): NetworkState =
        keywordAddRepository.getKeywordSiteRecommendation()

}