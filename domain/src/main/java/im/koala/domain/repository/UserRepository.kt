package im.koala.domain.repository

import im.koala.bcsd.state.NetworkState
import im.koala.domain.model.KeywordAddResponse

interface UserRepository {
    suspend fun getKeyword(): NetworkState
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): NetworkState
}