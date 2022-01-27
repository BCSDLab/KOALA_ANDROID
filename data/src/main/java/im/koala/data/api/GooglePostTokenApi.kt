package im.koala.data.api

import im.koala.data.constants.GOOGLE_TOKEN
import im.koala.data.entity.GooglePostTokenRequestEntity
import im.koala.data.entity.TokenEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GooglePostTokenApi {
    @POST(GOOGLE_TOKEN)
    suspend fun postGoogleToken(@Body request: GooglePostTokenRequestEntity): Response<TokenEntity>
}