package im.koala.data.mapper.history

import im.koala.data.api.response.history.HistoryResponse
import im.koala.data.api.response.history.ScrapResponse
import im.koala.data.mapper.site.toSite
import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.entity.keyword.KeywordNotice

fun HistoryResponse.toKeywordNotice(): KeywordNotice {
    return KeywordNotice(
        id = this.id,
        site = this.site.toSite(),
        title = this.title,
        url = this.url,
        createdAt = this.createdAt.substring(5,7)+"/"+this.createdAt.substring(8,10)+" - "+this.createdAt.substring(11,16),
        isRead = this.isRead,
        isChecked = false
    )
}

fun ScrapResponse.toScrapNotice(): ScrapNotice {
    return ScrapNotice(
        id = this.id,
        site = this.site.toSite(),
        title = this.title,
        url = this.url,
        createdAt = this.createdAt,
        userScrapedId = this.userScrapId,
        crawlingAt = this.crawlingAt,
        isChecked = false,
        memoCheck = null
    )
}