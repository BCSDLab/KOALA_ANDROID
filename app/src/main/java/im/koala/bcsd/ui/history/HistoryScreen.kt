package im.koala.bcsd.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.button.KoalaBackgroundButton
import im.koala.bcsd.ui.theme.*

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel
) {
    var allNoticeState = remember { mutableStateOf(true) }
    historyViewModel.updateHistory()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HistoryTab(
            modifier = modifier,
            allNoticeState = allNoticeState
        )
        DrawHistoryDivider(modifier = modifier)
        if (allNoticeState.value) {
            HistoryNoticeScreen(
                modifier = modifier,
                historyViewModel = historyViewModel
            )
        } else {
            StorageScreen(modifier = modifier)
        }
    }
}

@Composable
fun DrawHistoryDivider(modifier: Modifier = Modifier) {
    KoalaTheme {
        Divider(
            color = GrayBorder,
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
        )
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

