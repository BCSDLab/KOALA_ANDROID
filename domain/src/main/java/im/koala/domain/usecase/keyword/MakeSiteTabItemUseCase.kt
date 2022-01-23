package im.koala.domain.usecase.keyword

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class MakeSiteTabItemUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator fun invoke(keywordNotices: List<KeywordNotice>): Map<Site, String> {
        return keywordNotices
            .map { it.site }
            .toSet()
            .map { it to keywordRepository.getSiteLocalizedMessage(it) }
            .toMap()
    }
}