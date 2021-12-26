package im.koala.bcsd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import im.koala.bcsd.ui.button.KoalaButton
import im.koala.bcsd.ui.theme.KoalaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
        setContent {
            KoalaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        KoalaButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(48.dp),
                            onClick = {
                                throw RuntimeException("Test Crash")
                            }
                        ) {
                            Greeting("Android")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!"
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KoalaTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            TopAppBar {
                Row {
                }
                Text(text = "Hello Android!")
            }
        }
    }
}