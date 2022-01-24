package im.koala.domain.usecase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetDeviceTokenUseCase @Inject constructor() {
    operator fun invoke(): Flow<String> = callbackFlow {
        var eventsCollection = FirebaseMessaging.getInstance().token
        var subscription = eventsCollection.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    close()
                    return@OnCompleteListener
                }
                var deviceToken = task.result
                if (deviceToken != null) {
                    offer(deviceToken)
                }
            }
        )
        awaitClose { subscription.isComplete }
    }
}