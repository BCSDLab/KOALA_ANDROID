package im.koala.domain.entity.history

import im.koala.domain.entity.keyword.Site

data class HistoryNotice(
    val id: Int,
    val site: Site,
    val title: String,
    val url: String,
    val createdAt: String,
    val isRead: Boolean,
    val isChecked: Boolean,
    val crawlingId: Int
)