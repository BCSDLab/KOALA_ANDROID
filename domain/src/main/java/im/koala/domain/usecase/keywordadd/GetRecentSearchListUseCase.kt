package im.koala.domain.usecase.keywordadd

import im.koala.domain.repository.KeywordAddRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecentSearchListUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    operator fun invoke(key: String): Flow<List<String>> = flow {
        val list = keywordAddRepository.getRecentSearchList(key)
        emit(list)
    }
}