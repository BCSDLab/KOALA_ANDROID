package im.koala.data.repository.local

import im.koala.domain.model.TokenResponse

interface UserLocalDataSource {
    fun saveToken(tokenResponse: TokenResponse)
    fun saveSnsToken(snsType: String, token: String)
}