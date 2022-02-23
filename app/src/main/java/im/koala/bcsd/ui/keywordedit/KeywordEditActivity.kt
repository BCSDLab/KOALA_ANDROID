package im.koala.bcsd.ui.keywordedit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import im.koala.bcsd.R
import im.koala.bcsd.ui.keywordadd.KeywordAddScreen
import im.koala.bcsd.ui.keywordadd.KeywordViewModel
import im.koala.bcsd.ui.main.MainViewModel
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.data.api.response.keywordadd.KeywordAddResponseUi

@AndroidEntryPoint
@ExperimentalMaterialApi
class KeywordEditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val keywordViewModel: KeywordViewModel by viewModels()
        val mainViewModel: MainViewModel by viewModels()
        setContent {
            KoalaTheme {

            }
        }
    }
}