package im.koala.bcsd.ui.keyworddetail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import im.koala.bcsd.ui.keyworddetail.compose.KeywordDetailScreen
import im.koala.bcsd.ui.keyworddetail.viewmodel.KeywordDetailViewModel
import im.koala.bcsd.ui.theme.KoalaTheme

@AndroidEntryPoint
class KeywordDetailActivity : ComponentActivity() {

    private val keywordDetailViewModel: KeywordDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoalaTheme {
                KeywordDetailScreen(
                    keywordDetailViewModel = keywordDetailViewModel
                ) {
                    finish()
                }
            }
        }
    }
}