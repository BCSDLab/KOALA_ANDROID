package im.koala.bcsd.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.AndroidEntryPoint
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.data.constants.ACCESS_TOKEN

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoalaTheme {
                MainScreen()
            }
        }
    }
}