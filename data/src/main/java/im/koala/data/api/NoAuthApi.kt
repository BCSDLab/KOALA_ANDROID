package im.koala.data.api

import im.koala.data.constants.SNSLOGIN
import im.koala.data.entity.TokenBodyEntity
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NoAuthApi {
    @POST(SNSLOGIN)
    suspend fun postSnsLogin(@Header("Authorization") accessToken: String, @Path("snsType") snsType: String): Response<TokenBodyEntity>

}