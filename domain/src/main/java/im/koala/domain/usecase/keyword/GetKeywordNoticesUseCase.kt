package im.koala.domain.usecase.keyword

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.KeywordNoticeReadFilter
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class GetKeywordNoticesUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    suspend operator fun invoke(
        keyword: String,
        search: String = "",
        site: Site = Site.All,
        keywordNoticeReadFilter: KeywordNoticeReadFilter = KeywordNoticeReadFilter.None
    ): List<KeywordNotice> {
        val keywordNotices = keywordRepository.getKeywordNotices(
            keyword = keyword,
            search = if(search.isBlank()) null else search,
            site = if(site == Site.All) null else site
        ).filter {
            when (keywordNoticeReadFilter) {
                KeywordNoticeReadFilter.None -> true
                KeywordNoticeReadFilter.ShowOnlyReadNotice -> it.isRead
                KeywordNoticeReadFilter.ShowOnlyUnreadNotice -> !it.isRead
            }
        }

        return keywordNotices
    }
}