package im.koala.domain.entity.history

data class Memo(
    val userScrapedId: Int,
    val updatedAt: String,
    val createdAt: String,
    val memo: String
)