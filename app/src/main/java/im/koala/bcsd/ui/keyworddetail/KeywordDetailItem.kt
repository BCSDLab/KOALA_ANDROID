package im.koala.bcsd.ui.keyworddetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.ui.button.KoalaCheckBox
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.util.toFormattedDate

@Composable
fun KeywordDetailItem(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    date: String,
    isChecked: Boolean = false,
    isRead: Boolean = false,
    onCheckedChange: (checked: Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        KoalaCheckBox(checked = isChecked, onCheckedChange = onCheckedChange)
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .alignByBaseline(),
                    text = title,
                    color = if(isRead) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline(),
                    text = date,
                    style = MaterialTheme.typography.caption,
                    fontSize = 11.sp,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                text = content,
                style = MaterialTheme.typography.body2,
                color = if(isRead) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview("Keyword detail item")
@Composable
private fun KeywordDetailItemPreview() {
    val checked = rememberSaveable { mutableStateOf(false) }

    KoalaTheme {
        Surface {
            KeywordDetailItem(
                title = "test",
                content = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
                date = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
                isChecked = checked.value,
                onCheckedChange = {
                    checked.value = it
                })
        }
    }
}

@Preview("Keyword detail item(checked)")
@Composable
private fun KeywordDetailItemCheckedPreview() {
    KoalaTheme {
        Surface {
            KeywordDetailItem(
                title = "test",
                content = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
                date = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
                isChecked = true,
                onCheckedChange = {})
        }
    }
}

@Preview("Keyword detail item(read)")
@Composable
private fun KeywordDetailItemReadPreview() {
    KoalaTheme {
        Surface {
            KeywordDetailItem(
                title = "test",
                content = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
                date = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
                isRead = true,
                onCheckedChange = {})
        }
    }
}

@Preview("Keyword detail item(read, checked)")
@Composable
private fun KeywordDetailItemReadCheckedPreview() {
    KoalaTheme {
        Surface {
            KeywordDetailItem(
                title = "test",
                content = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
                date = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
                isChecked = true,
                isRead = true,
                onCheckedChange = {})
        }
    }
}