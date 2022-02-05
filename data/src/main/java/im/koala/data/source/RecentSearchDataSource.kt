package im.koala.data.source

interface RecentSearchDataSource {
    suspend fun getRecentSearchList():List<String>
    suspend fun setRecentSearchList(recentSearchList:List<String>)
}