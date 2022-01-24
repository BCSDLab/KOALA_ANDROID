package im.koala.domain.repository

import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.model.TokenResponse

interface UserRepository {
    suspend fun getKeyword(onSuccess: (MutableList<KeywordResponse>) -> Unit, onFail: (CommonResponse) -> Unit)
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): NetworkState
}