package im.koala.domain.usecase.keyword

import im.koala.domain.entity.keyword.KeywordListItem
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class MakeSiteTabItemUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator fun invoke(keywordListItems: List<KeywordListItem>): Map<Site, String> {
        return keywordListItems
            .map { it.site }
            .toSet()
            .map { it to keywordRepository.getSiteLocalizedMessage(it) }
            .toMap()
    }
}