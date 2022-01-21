package im.koala.bcsd.ui.keyworddetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.domain.entity.keyword.KeywordItemReadFilter

@Composable
fun KeywordDetailPopupMenu(
    expanded: Boolean = false,
    keywordItemReadFilter: KeywordItemReadFilter = KeywordItemReadFilter.None,
    onDismissRequest: () -> Unit,
    onSelectionChange: (KeywordItemReadFilter) -> Unit
) {
    DropdownMenu(
        modifier = Modifier.padding(horizontal = 4.dp),
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        CompositionLocalProvider(
            LocalContentColor provides
                if (keywordItemReadFilter == KeywordItemReadFilter.ShowOnlyUnreadItem) {
                    MaterialTheme.colors.onPrimary
                } else {
                    MaterialTheme.colors.onBackground
                }
        ) {
            DropdownItem(
                onClick = {
                    onSelectionChange(
                        if (keywordItemReadFilter == KeywordItemReadFilter.ShowOnlyUnreadItem) {
                            KeywordItemReadFilter.None
                        } else {
                            KeywordItemReadFilter.ShowOnlyUnreadItem
                        }
                    )
                    onDismissRequest()
                },
                painter = painterResource(id = R.drawable.ic_mail),
                text = stringResource(R.string.keyword_detail_menu_not_read_item)
            )
        }

        CompositionLocalProvider(
            LocalContentColor provides
                if (keywordItemReadFilter == KeywordItemReadFilter.ShowOnlyReadItem) {
                    MaterialTheme.colors.onPrimary
                } else {
                    MaterialTheme.colors.onBackground
                }
        ) {
            DropdownItem(
                onClick = {
                    onSelectionChange(
                        if (keywordItemReadFilter == KeywordItemReadFilter.ShowOnlyReadItem) {
                            KeywordItemReadFilter.None
                        } else {
                            KeywordItemReadFilter.ShowOnlyReadItem
                        }
                    )
                    onDismissRequest()
                },
                painter = painterResource(id = R.drawable.ic_mail_open),
                text = stringResource(R.string.keyword_detail_menu_read_item)
            )
        }
    }
}

@Composable
private fun DropdownItem(
    onClick: () -> Unit,
    painter: Painter,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(16.dp),
            painter = painter,
            contentDescription = ""
        )

        Text(
            text = text,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview
@Composable
fun KeywordDetailPopupMenuPreview() {
    KoalaTheme {
        val keywordItemReadFilter = rememberSaveable { mutableStateOf<KeywordItemReadFilter>(KeywordItemReadFilter.None) }
        KeywordDetailPopupMenu(
            expanded = true,
            onDismissRequest = {},
            onSelectionChange = {
                keywordItemReadFilter.value = it
            },
            keywordItemReadFilter = keywordItemReadFilter.value
        )
    }
}