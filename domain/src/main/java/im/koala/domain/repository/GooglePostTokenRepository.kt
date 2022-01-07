package im.koala.domain.repository

interface GooglePostTokenRepository {
    suspend fun postGoogleToken(
        clientId: String,
        clientSecret: String,
        authCode: String,
        onSuccess: (String) -> Unit,
        onFail: (Unit) -> Unit
    )
}