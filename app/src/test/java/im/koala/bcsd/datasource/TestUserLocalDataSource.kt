package im.koala.bcsd.datasource

import im.koala.data.repository.local.UserLocalDataSource
import im.koala.domain.model.TokenResponse

class TestUserLocalDataSource : UserLocalDataSource {
    override fun saveToken(tokenResponse: TokenResponse) {
    }
}