package im.koala.bcsd.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.ui.button.KoalaCheckBox
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.util.compose.LocalizedMessage
import im.koala.domain.entity.history.HistoryNotice
import im.koala.domain.entity.keyword.Site

@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    history: HistoryNotice,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 16.dp, top = 16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            KoalaCheckBox(
                checked = history.isChecked,
                onCheckedChange = onCheckedChange,
                modifier = modifier
                    .width(16.dp)
                    .height(21.dp)
                    .padding(top = 3.dp, bottom = 2.dp)
            )
            Text(
                text = history.site.LocalizedMessage(),
                style = MaterialTheme.typography.body1,
                color = if (history.isRead) {
                    MaterialTheme.colors.onBackground
                } else {
                    MaterialTheme.colors.onPrimary
                },
                modifier = modifier
                    .padding(start = 8.dp)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = history.createdAt,
                    style = TextStyle(
                        color = MaterialTheme.colors.onBackground,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp
                    )
                )
            }
        }
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 29.dp, top = 8.dp),
            text = history.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body2,
            color = if (history.isRead) {
                MaterialTheme.colors.onBackground
            } else {
                MaterialTheme.colors.onPrimary
            }
        )
        Divider(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 14.dp, top = 15.dp),
            color = GrayBorder
        )
    }
}

@Preview
@Composable
fun previewHistoryItem() {
    KoalaTheme {
        Surface {
            HistoryItem(
                history = HistoryNotice(
                    id = 1,
                    site = Site.Dorm,
                    title = "테스트",
                    url = "url",
                    createdAt = "0/00 - 00:00",
                    isRead = false,
                    isChecked = false,
                    crawlingId = 1
                ),
                onCheckedChange = {}
            )
        }
    }
}