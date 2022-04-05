package im.koala.bcsd.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.ui.theme.Typography

@Composable
fun HistoryTab(
    modifier: Modifier = Modifier,
    allNoticeState: MutableState<Boolean>
) {
    Row (
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                onClick = { allNoticeState.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                ),
                modifier = modifier
                    .height(49.dp)
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                if (allNoticeState.value) {
                    Text (
                        text = stringResource(id = R.string.history_all_notice),
                        style = Typography.h2,
                        color = MaterialTheme.colors.onPrimary
                    )
                } else {
                    Text (
                        text = stringResource(id = R.string.history_all_notice),
                        style = Typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
            Divider(
                color = GrayBorder,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (allNoticeState.value) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Divider(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = modifier
                            .width(32.dp)
                            .height(2.dp)
                    )
                }
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                onClick = { allNoticeState.value = false },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                ),
                modifier = modifier
                    .height(49.dp)
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                if (allNoticeState.value) {
                    Text (
                        text = stringResource(id = R.string.history_storage),
                        style = Typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                } else {
                    Text (
                        text = stringResource(id = R.string.history_storage),
                        style = Typography.h2,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
            Divider(
                color = GrayBorder,
                modifier = modifier
                    .fillMaxWidth()
            )
            if(!allNoticeState.value) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Divider(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = modifier
                            .width(32.dp)
                            .height(2.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HistoryTabPreview() {
    val testState = remember { mutableStateOf(true) }
    KoalaTheme(
    ) {
        HistoryTab(allNoticeState = testState)
    }
}