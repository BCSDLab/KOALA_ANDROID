package im.koala.data.repository.local

interface KeywordAddLocalDataSource {
    suspend fun getRecentSearchList(key: String): List<String>
    suspend fun setRecentSearchList(key: String, recentSearchList: List<String>)
}