package im.koala.domain.usecase

import im.koala.domain.repository.GooglePostTokenRepository
import javax.inject.Inject

class GooglePostAccessTokenUseCase @Inject constructor(
    private val googlePostTokenRepository: GooglePostTokenRepository
) {
    suspend operator fun invoke(
        clientId: String,
        clientSecret: String,
        authCode: String,
        onSuccess: (String) -> Unit,
        onFail: (Unit) -> Unit
    ) {
        googlePostTokenRepository.postGoogleToken(
            clientId = clientId,
            clientSecret = clientSecret,
            authCode = authCode,
            onSuccess = onSuccess,
            onFail = onFail
        )
    }
}