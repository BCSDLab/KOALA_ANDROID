package im.koala.domain.repository

import im.koala.domain.state.NetworkState

interface UserRepository {
    suspend fun getKeyword(): NetworkState
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): NetworkState
}