package im.koala.bcsd.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.theme.Typography

@Composable
fun HistoryTab(
    modifier: Modifier = Modifier,
    allNoticeState: MutableState<Boolean>
) {
    Row (
        modifier = modifier
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
