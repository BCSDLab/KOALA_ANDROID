package im.koala.domain.usecase.keyword

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.KeywordNoticeReadFilter
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class GetKeywordNoticesUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator fun invoke(
        keyword: String,
        search: String = "",
        site: Site = Site.All,
        keywordNoticeReadFilter: KeywordNoticeReadFilter = KeywordNoticeReadFilter.None
    ): List<KeywordNotice> {
        val searchWord = if(search.isBlank()) null else search
        val keywordNotices = keywordRepository.getKeywordNotices(keyword, searchWord, site)

        return keywordNotices.filter {
            when (keywordNoticeReadFilter) {
                KeywordNoticeReadFilter.None -> true
                KeywordNoticeReadFilter.ShowOnlyReadNotice -> it.isRead
                KeywordNoticeReadFilter.ShowOnlyUnreadNotice -> !it.isRead
            }
        }
    }
}