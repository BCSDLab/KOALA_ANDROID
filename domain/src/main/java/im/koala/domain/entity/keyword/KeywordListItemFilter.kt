package im.koala.domain.entity.keyword

data class KeywordListItemFilter(
    val search: String,
    val site: Site,
    val keywordItemReadFilter: KeywordItemReadFilter
) {
    companion object {
        fun default() = KeywordListItemFilter(
            search = "",
            site = Site.All,
            keywordItemReadFilter = KeywordItemReadFilter.None
        )
    }
}

sealed class KeywordItemReadFilter {
    object None : KeywordItemReadFilter()
    object ShowOnlyReadItem : KeywordItemReadFilter()
    object ShowOnlyUnreadItem : KeywordItemReadFilter()
}