package im.koala.domain.usecase.keywordadd

import im.koala.domain.repository.KeywordAddRepository
import javax.inject.Inject

class SetRecentSearchListUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(key: String, searchList: List<String>) {
        keywordAddRepository.setRecentSearchList(key, searchList)
    }
}