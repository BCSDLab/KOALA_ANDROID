package im.koala.domain.usecase.keywordadd

import im.koala.domain.state.Result
import im.koala.domain.repository.KeywordAddRepository
import javax.inject.Inject

class GetSiteSearchUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(site: String): Result =
        keywordAddRepository.getKeywordSiteSearch(site)
}