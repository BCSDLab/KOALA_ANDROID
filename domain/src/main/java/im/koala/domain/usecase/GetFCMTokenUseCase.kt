package im.koala.domain.usecase

import android.util.Log
import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetFCMTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<String> = callbackFlow {
        userRepository.getFCMToken(
            success = {
                trySend(it)
            }, fail = {
            it?.let { trySend(it) }
        }
        )
        awaitClose { log() }
    }
    private fun log() {
        Log.e("GetDeviceTokenUseCase", "finish")
    }
}