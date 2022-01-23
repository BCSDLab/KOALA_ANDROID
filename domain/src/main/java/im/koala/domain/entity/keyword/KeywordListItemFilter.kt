package im.koala.domain.entity.keyword

data class KeywordListItemFilter(
    val search: String,
    val site: Site,
    val keywordNoticeReadFilter: KeywordNoticeReadFilter
) {
    companion object {
        fun default() = KeywordListItemFilter(
            search = "",
            site = Site.All,
            keywordNoticeReadFilter = KeywordNoticeReadFilter.None
        )
    }
}

sealed class KeywordNoticeReadFilter {
    object None : KeywordNoticeReadFilter()
    object ShowOnlyReadNotice : KeywordNoticeReadFilter()
    object ShowOnlyUnreadNotice : KeywordNoticeReadFilter()
}