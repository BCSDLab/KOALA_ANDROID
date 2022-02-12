package im.koala.domain.repository

import im.koala.domain.state.ApiResponse

interface UserRepository {
    suspend fun postSnsLogin(snsType: String, accessToken: String, deviceToken: String): ApiResponse
}