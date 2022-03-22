package im.koala.bcsd.util.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import im.koala.data.constants.FCM_TOKEN
import im.koala.domain.usecase.GetFCMTokenUseCase
import javax.inject.Inject

@AndroidEntryPoint
class KoalaFirebaseNotificationService: FirebaseMessagingService() {

    @Inject lateinit var getFCMTokenUseCase: GetFCMTokenUseCase

    override fun onCreate() {
        super.onCreate()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    override fun onNewToken(newToken: String) {
        Log.d(TAG, "New token: $newToken")

        Hawk.put(FCM_TOKEN, newToken)
    }

    companion object {
        private const val TAG = "KoalaFCMService"
    }
}