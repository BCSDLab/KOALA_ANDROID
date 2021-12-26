package im.koala.data.api

import im.koala.data.constant.KOALA_API_URL_ACCOUNT_CHECK
import im.koala.data.constant.KOALA_API_URL_EMAIL_CHECK
import im.koala.data.constant.KOALA_API_URL_NICKNAME_CHECK
import retrofit2.http.GET
import retrofit2.http.Query

interface NoAuthApi {
    @GET(KOALA_API_URL_ACCOUNT_CHECK)
    suspend fun checkAccount(
        @Query("account") account: String
    ): String

    @GET(KOALA_API_URL_NICKNAME_CHECK)
    suspend fun checkNickname(
        @Query("nickname") nickname: String
    ): String

    @GET(KOALA_API_URL_EMAIL_CHECK)
    suspend fun checkEmail(
        @Query("email") email: String
    ): String


}