package im.koala.domain.entity.history

import im.koala.domain.entity.keyword.Site

data class ScrapNotice(
    val id: Int,
    val userScrapedId: Int,
    val site: Site,
    val title: String,
    val url: String,
    val createdAt: String,
    val crawlingAt: String,
    val isChecked: Boolean,
    val memoCheck: Memo?
)
