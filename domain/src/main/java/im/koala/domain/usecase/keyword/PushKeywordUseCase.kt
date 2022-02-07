package im.koala.domain.usecase.keyword

import im.koala.domain.state.NetworkState
import im.koala.domain.model.KeywordAddResponse
import im.koala.domain.repository.KeywordAddRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PushKeywordUseCase @Inject constructor (
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(keywordResponse: KeywordAddResponse): NetworkState = keywordAddRepository.pushKeyword(keywordResponse)
}