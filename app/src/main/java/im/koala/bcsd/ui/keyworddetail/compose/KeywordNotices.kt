package im.koala.bcsd.ui.keyworddetail.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaBackgroundButton
import im.koala.bcsd.ui.button.KoalaCheckBox
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.KeywordNoticeReadFilter

@Composable
fun KeywordNotices(
    keywordNotices: List<KeywordNotice>,
    keywordNoticeReadFilter: KeywordNoticeReadFilter,
    onKeywordNoticeReadFilterChanged: (KeywordNoticeReadFilter) -> Unit,
    onKeywordNoticeClicked: (KeywordNotice) -> Unit,
    onCheckedChange: (List<KeywordNotice>, Boolean) -> Unit,
    onKeepButtonClicked: (List<KeywordNotice>) -> Unit,
    onRemoveButtonClicked: (List<KeywordNotice>) -> Unit
) {
    val popupMenuExpanded = rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            KoalaCheckBox(
                checked = keywordNotices.find { !it.isChecked } == null,
                onCheckedChange = { isChecked ->
                    keywordNotices.forEach { onCheckedChange(keywordNotices, isChecked) }
                }
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.keyword_detail_select_all)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            KeywordScreenBackgroundButton(
                text = stringResource(R.string.keyword_detail_item_inbox),
                iconPainter = painterResource(id = R.drawable.ic_inbox_in),
                onClick = { onKeepButtonClicked(keywordNotices.filter { it.isChecked }) }
            )

            KeywordScreenBackgroundButton(
                modifier = Modifier.padding(start = 4.dp),
                text = stringResource(R.string.keyword_detail_item_delete),
                iconPainter = painterResource(id = R.drawable.ic_trash),
                onClick = { onRemoveButtonClicked(keywordNotices.filter { it.isChecked }) }
            )

            IconButton(onClick = {
                popupMenuExpanded.value = true
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dots_vertical),
                    contentDescription = ""
                )

                KeywordDetailPopupMenu(
                    expanded = popupMenuExpanded.value,
                    keywordNoticeReadFilter = keywordNoticeReadFilter,
                    onDismissRequest = { popupMenuExpanded.value = false },
                    onSelectionChange = onKeywordNoticeReadFilterChanged
                )
            }
        }
    }

    LazyColumn {
        items(
            items = keywordNotices,
            key = { it.id }
        ) { keywordNotice ->
            KeywordDetailItem(
                keywordNotice = keywordNotice,
                onCheckedChange = { isChecked ->
                    onCheckedChange(listOf(keywordNotice), isChecked)
                },
                onItemClick = onKeywordNoticeClicked
            )
        }
    }
}

@Composable
private fun KeywordScreenBackgroundButton(
    modifier: Modifier = Modifier,
    text: String,
    iconPainter: Painter,
    onClick: () -> Unit
) {
    KoalaBackgroundButton(
        modifier = modifier.height(34.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = iconPainter, contentDescription = "")
            Text(text = text)
        }
    }
}