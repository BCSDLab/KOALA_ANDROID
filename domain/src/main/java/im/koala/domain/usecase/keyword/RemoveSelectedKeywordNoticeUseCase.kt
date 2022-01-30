package im.koala.domain.usecase.keyword

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class RemoveSelectedKeywordNoticeUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    suspend operator fun invoke(keywordNotices: List<KeywordNotice>) {
        keywordRepository.removeSelectedKeywordNotices(keywordNotices)
    }
}