package im.koala.domain.usecase.keyword

import im.koala.domain.entity.keyword.KeywordItemReadFilter
import im.koala.domain.entity.keyword.KeywordListItemFilter
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import javax.inject.Inject

class GetKeywordItemListUseCase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator fun invoke(
        keyword: String,
        filter: KeywordListItemFilter = KeywordListItemFilter.default()
    ): List<KeywordNotice> {
        val keywordNotices = keywordRepository.getKeywordNotices(keyword)

        return if (filter != null) {
            keywordNotices
                // #1 Site Filtering
                .filter { if(filter.site == Site.All) true else it.site == filter.site }
                // #2 Keyword read filtering
                .filter {
                    when (filter.keywordItemReadFilter) {
                        KeywordItemReadFilter.None -> true
                        KeywordItemReadFilter.ShowOnlyReadItem -> it.isRead
                        KeywordItemReadFilter.ShowOnlyUnreadItem -> !it.isRead
                    }
                }
                // #3 Search filtering
                .filter {
                    if (filter.search.isEmpty()) {
                        true
                    } else {
                        it.title.contains(filter.search) ||
                            keywordRepository.getSiteLocalizedMessage(it.site)
                                .contains(filter.search)
                    }
                }
        } else {
            keywordNotices
        }
    }
}