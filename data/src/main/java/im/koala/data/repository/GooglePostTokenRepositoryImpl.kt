package im.koala.data.repository

import im.koala.data.api.GooglePostTokenApi
import im.koala.data.entity.GooglePostTokenRequestEntity
import im.koala.domain.repository.GooglePostTokenRepository
import javax.inject.Inject

class GooglePostTokenRepositoryImpl @Inject constructor(
    private val googlePostTokenApi: GooglePostTokenApi
) : GooglePostTokenRepository {
    override suspend fun postGoogleToken(
        clientId: String,
        clientSecret: String,
        authCode: String,
        onSuccess: (String) -> Unit,
        onFail: (Unit) -> Unit
    ) {
        val response = googlePostTokenApi.postGoogleToken(
            GooglePostTokenRequestEntity(
                clientId = clientId,
                clientSecret = clientSecret,
                code = authCode,
                grantType = "authorization_code",
                redirectUri = ""
            )
        )
        if (response.isSuccessful) {
            val accessToken = response.body()?.accessToken
            if (accessToken != null) {
                onSuccess(accessToken)
            } else {
                onFail
            }
        } else {
            onFail
        }
    }
}