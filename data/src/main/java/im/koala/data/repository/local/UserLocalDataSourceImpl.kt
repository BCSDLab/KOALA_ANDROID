package im.koala.data.repository.local

import com.orhanobut.hawk.Hawk
import im.koala.data.constants.ACCESS_TOKEN
import im.koala.data.constants.REFRESH_TOKEN
import im.koala.domain.model.TokenResponse
import javax.inject.Inject

class UserLocalDataSourceImpl@Inject constructor() : UserLocalDataSource {
    override fun saveToken(tokenResponse: TokenResponse) {
        Hawk.put(ACCESS_TOKEN, tokenResponse.accessToken)
        Hawk.put(REFRESH_TOKEN, tokenResponse.refreshToken)
    }
}