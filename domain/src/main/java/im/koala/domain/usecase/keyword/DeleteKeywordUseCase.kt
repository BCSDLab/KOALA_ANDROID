package im.koala.domain.usecase.keyword

import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.state.NetworkState
import javax.inject.Inject

class DeleteKeywordUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(keyword:String): NetworkState = keywordAddRepository.deleteKeyword(keyword)
}