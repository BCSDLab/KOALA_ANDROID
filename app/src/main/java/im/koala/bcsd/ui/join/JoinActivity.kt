package im.koala.bcsd.ui.join

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.res.stringResource
import im.koala.bcsd.R
import im.koala.bcsd.ui.theme.KoalaTheme

class JoinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoalaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Text(text = stringResource(id = R.string.join_koala))
                }
            }
        }
    }
}