package im.koala.domain.entity.keyword

data class KeywordNotice(
    val id: Int,
    val site: Site,
    val title: String,
    val url: String,
    val createdAt: String,
    val isRead: Boolean,
    val isChecked: Boolean = false
)
