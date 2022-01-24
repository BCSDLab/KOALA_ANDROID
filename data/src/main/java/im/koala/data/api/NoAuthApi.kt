package im.koala.data.api

import im.koala.data.api.request.signup.SignUpRequest
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.api.response.signup.SignUpResultResponse
import im.koala.data.constant.KOALA_API_URL_ACCOUNT_CHECK
import im.koala.data.constant.KOALA_API_URL_EMAIL_CHECK
import im.koala.data.constant.KOALA_API_URL_NICKNAME_CHECK
import im.koala.data.constant.KOALA_API_URL_SIGN_UP
import im.koala.data.constants.SNSLOGIN
import im.koala.data.entity.TokenBodyEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NoAuthApi {
    @GET(KOALA_API_URL_ACCOUNT_CHECK)
    suspend fun checkAccount(
        @Query("account") account: String
    ): ResponseWrapper<String>

    @GET(KOALA_API_URL_NICKNAME_CHECK)
    suspend fun checkNickname(
        @Query("nickname") nickname: String
    ): ResponseWrapper<String>

    @GET(KOALA_API_URL_EMAIL_CHECK)
    suspend fun checkEmail(
        @Query("email") email: String
    ): ResponseWrapper<String>

    @POST(KOALA_API_URL_SIGN_UP)
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): ResponseWrapper<SignUpResultResponse>

    @POST(SNSLOGIN)
    suspend fun postSnsLogin(
        @Header("Authorization") accessToken: String,
        @Path("snsType") snsType: String,
        @Query("device_token") deviceToken: String
    ): Response<TokenBodyEntity>
}