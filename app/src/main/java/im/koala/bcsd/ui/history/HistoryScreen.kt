package im.koala.bcsd.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import im.koala.bcsd.ui.button.KoalaBackgroundButton

@ExperimentalMaterialApi
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel
) {
    var allNoticeState = remember { mutableStateOf(true) }
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HistoryTab(
            allNoticeState = allNoticeState
        )
        if (allNoticeState.value) {
            HistoryNoticeScreen(
                historyViewModel = historyViewModel
            )
        } else {
            StorageScreen(
                historyViewModel = historyViewModel
            )
        }
    }
}

@Composable
fun HistoryCommandButton(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    KoalaBackgroundButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = ""
        )
        Text(
            text = text
        )
    }
}

//@Preview
//@Composable
//fun previewHistoryScreen() {
//    KoalaTheme {
//        HistoryScreen()
//    }
//}

