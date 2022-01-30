package im.koala.bcsd.ui.keyworddetail.state

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.KeywordNoticeReadFilter
import im.koala.domain.entity.keyword.Site

data class KeywordDetailUiState(
    val keyword: String = "",
    val keywordNotices: List<KeywordNotice> = listOf(),
    val keywordSiteTabs: List<Pair<Site, String>> = listOf(Site.All to Site.All.toString()),
    val selectedSite: Site = Site.All,
    val keywordNoticeReadFilter: KeywordNoticeReadFilter = KeywordNoticeReadFilter.None
)