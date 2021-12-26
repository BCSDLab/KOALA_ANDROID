package im.koala.data.api

import retrofit2.http.GET

interface NoAuthApi {
    @GET
    suspend fun checkAccount(id: String): String

    @GET
    suspend fun checkNickname(nickname: String): String

    @GET
    suspend fun checkEmail(email: String): String
}