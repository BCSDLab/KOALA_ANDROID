package im.koala.bcsd.ui.keyworddetail.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.ui.button.KoalaCheckBox
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.util.compose.LocalizedMessage
import im.koala.bcsd.util.toFormattedDate
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site

@Composable
fun KeywordDetailItem(
    modifier: Modifier = Modifier,
    keywordNotice: KeywordNotice,
    onCheckedChange: (checked: Boolean) -> Unit,
    onItemClick: (KeywordNotice) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(keywordNotice)
            }
            .padding(16.dp)
    ) {
        KoalaCheckBox(checked = keywordNotice.isChecked, onCheckedChange = onCheckedChange)
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
                    text = keywordNotice.site.LocalizedMessage(),
                    color = if (!keywordNotice.isRead) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    modifier = Modifier
                        .alignByBaseline(),
                    text = keywordNotice.createdAt,
                    style = MaterialTheme.typography.caption,
                    fontSize = 11.sp,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                text = keywordNotice.title,
                style = MaterialTheme.typography.body2,
                color = if (!keywordNotice.isRead) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview("Keyword detail item")
@Composable
private fun KeywordDetailItemPreview() {
    val keywordNotice = KeywordNotice(
        id = 1,
        site = Site.Portal,
        title = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
        createdAt = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
        url = "",
        isChecked = false,
        isRead = false
    )

    KoalaTheme {
        Surface {
            KeywordDetailItem(
                keywordNotice = keywordNotice,
                onCheckedChange = {},
                onItemClick = {}
            )
        }
    }
}

@Preview("Keyword detail item(checked)")
@Composable
private fun KeywordDetailItemCheckedPreview() {
    val keywordNotice = KeywordNotice(
        id = 1,
        site = Site.Portal,
        title = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
        createdAt = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
        url = "",
        isChecked = false,
        isRead = false
    )

    KoalaTheme {
        Surface {
            KeywordDetailItem(
                keywordNotice = keywordNotice,
                onCheckedChange = {},
                onItemClick = {}
            )
        }
    }
}

@Preview("Keyword detail item(read)")
@Composable
private fun KeywordDetailItemReadPreview() {
    val keywordNotice = KeywordNotice(
        id = 1,
        site = Site.Portal,
        title = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
        createdAt = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
        url = "",
        isRead = true,
        isChecked = false
    )

    KoalaTheme {
        Surface {
            KeywordDetailItem(
                keywordNotice = keywordNotice,
                onCheckedChange = {},
                onItemClick = {}
            )
        }
    }
}

@Preview("Keyword detail item(read, checked)")
@Composable
private fun KeywordDetailItemReadCheckedPreview() {
    val keywordNotice = KeywordNotice(
        id = 1,
        site = Site.Portal,
        title = "test is skybodakoreatech ibnidatest is skybodakoreatech ibnidatest is skybodakoreatech ibnida",
        createdAt = System.currentTimeMillis().toFormattedDate("YYYY-MM-DD"),
        url = "",
        isChecked = false,
        isRead = true
    )

    KoalaTheme {
        Surface {
            KeywordDetailItem(
                keywordNotice = keywordNotice,
                onCheckedChange = {},
                onItemClick = {}
            )
        }
    }
}