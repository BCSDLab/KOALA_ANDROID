package im.koala.data.repository.local

import com.orhanobut.hawk.Hawk
import im.koala.data.constants.ACCESS_TOKEN
import im.koala.data.constants.GOOGLE_TOKEN
import im.koala.data.constants.KAKAO_TOKEN
import im.koala.data.constants.NAVER_TOKEEN
import im.koala.data.constants.AUTO_LOGIN
import im.koala.data.constants.REFRESH_TOKEN
import im.koala.domain.constants.GOOGLE
import im.koala.domain.constants.KAKAO
import im.koala.domain.constants.NAVER
import im.koala.domain.model.TokenResponse
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor() : UserLocalDataSource {
    override fun saveToken(tokenResponse: TokenResponse) {
        Hawk.put(ACCESS_TOKEN, tokenResponse.accessToken)
        Hawk.put(REFRESH_TOKEN, tokenResponse.refreshToken)
    }

    override fun saveSnsToken(snsType: String, token: String) {
        when (snsType) {
            KAKAO -> Hawk.put(KAKAO_TOKEN, token)
            NAVER -> Hawk.put(NAVER_TOKEEN, token)
            GOOGLE -> Hawk.put(GOOGLE_TOKEN, token)
            else -> return
        }
    }
    override fun setAutoLoginState(autoLogin: Boolean) {
        Hawk.put(AUTO_LOGIN, autoLogin)
    }

    override fun isAutoLogin(): Boolean {
        return Hawk.get(AUTO_LOGIN, false) && Hawk.get(ACCESS_TOKEN, "") != ""
    }
}