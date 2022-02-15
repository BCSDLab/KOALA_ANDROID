package im.koala.domain.usecase.keywordadd

import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.state.Result
import javax.inject.Inject

class DeleteKeywordUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(keyword: String): Result =
        keywordAddRepository.deleteKeyword(keyword)
}