package im.koala.bcsd.ui.signup

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class SignUpContract : ActivityResultContract<Void?, String?>() {
    override fun createIntent(context: Context, input: Void?): Intent {
        return Intent(context, SignupActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return intent?.getStringExtra(LOGIN_ID)
    }

    companion object {
        const val LOGIN_ID = "LOGIN_ID"
    }
}