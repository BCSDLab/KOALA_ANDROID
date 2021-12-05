package im.koala.bcsd.ui.signup

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.theme.KoalaTheme

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        setContent {
            SignupContent()
        }
    }
}

@Preview
@Composable
fun SignupContent() {
    KoalaTheme {
        Scaffold(
            topBar = {
                KoalaTextAppBar(
                    title = stringResource(R.string.app_bar_title_signup),
                    showBackButton = true
                ) {}
            }
        ) {}
    }
}