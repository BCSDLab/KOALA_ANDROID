package im.koala.domain.usecase.keyword

import im.koala.domain.entity.keyword.KeywordListItem
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class KeepSelectedKeywordDetailItemUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
){
    operator fun invoke(keywordListItems: List<KeywordListItem>) {
        keywordRepository.keepSelectedKeywordItems(keywordListItems)
    }
}