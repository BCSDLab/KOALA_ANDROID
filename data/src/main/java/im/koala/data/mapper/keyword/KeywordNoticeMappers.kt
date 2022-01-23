package im.koala.data.mapper.keyword

import im.koala.data.api.response.keyword.KeywordNoticeResponse
import im.koala.data.mapper.site.toSite
import im.koala.domain.entity.keyword.KeywordNotice

fun KeywordNoticeResponse.toKeywordNotice() : KeywordNotice {
    return KeywordNotice(
        id = this.id,
        site = this.site.toSite(),
        title = this.title,
        url = this.url,
        createdAt = this.createdAt,
        isRead = this.isRead,
        isChecked = false
    )
}