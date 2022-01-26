package im.koala.data.api

import im.koala.data.constants.KEYWORD
import im.koala.data.entity.KeywordBodyEntity
import retrofit2.Response
import retrofit2.http.GET

interface AuthApi {
    @GET(KEYWORD)
    suspend fun getKeyword(): Response<KeywordBodyEntity>
}