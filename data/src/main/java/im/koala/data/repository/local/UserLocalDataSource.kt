package im.koala.data.repository.local

import im.koala.domain.model.TokenResponse

interface UserLocalDataSource {
    fun saveToken(tokenResponse: TokenResponse)
    suspend fun getRecentSearchList(key: String): List<String>
    suspend fun setRecentSearchList(key: String, recentSearchList: List<String>)
}