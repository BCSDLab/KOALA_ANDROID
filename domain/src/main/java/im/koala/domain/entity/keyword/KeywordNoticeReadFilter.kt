package im.koala.domain.entity.keyword

sealed class KeywordNoticeReadFilter {
    object None : KeywordNoticeReadFilter()
    object ShowOnlyReadNotice : KeywordNoticeReadFilter()
    object ShowOnlyUnreadNotice : KeywordNoticeReadFilter()
}