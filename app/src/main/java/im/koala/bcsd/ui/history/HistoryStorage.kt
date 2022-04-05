package im.koala.bcsd.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaCheckBox

@ExperimentalMaterialApi
@Composable
fun StorageScreen(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel
) {
    historyViewModel.updateStorage()
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(top = 17.dp, bottom = 2.dp)
        ) {
            KoalaCheckBox(
                modifier = modifier
                    .height(34.dp)
                    .width(42.dp)
                    .padding(top = 10.dp, bottom = 8.dp, start = 18.dp, end = 8.dp),
                checked = false,
                onCheckedChange = { historyViewModel.storageAllCheck(it) }
            )
            Text(
                text = stringResource(id = R.string.history_all_choice),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary,
                modifier = modifier
                    .padding(top = 8.dp, bottom = 8.dp)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                HistoryCommandButton(
                    modifier = modifier
                        .padding(end = 16.dp),
                    iconPainter = painterResource(id = R.drawable.ic_trash),
                    text = stringResource(id = R.string.history_delete),
                    onClick = { historyViewModel.deleteScrapNotice() }
                )
            }
        }
        LazyColumn {
            items(
                items = historyViewModel.storageUiState.scrapNotice,
                key = { it.id }
            ) { scrapNotice ->
                StorageItem(
                    modifier = modifier,
                    scrapNotice = scrapNotice,
                    onCheckedChange = {
                        historyViewModel.setStorageCheckState(listOf(scrapNotice),it)
                    },
                    onClickFinishButton = { scrapNotice, memo ->
                        historyViewModel.editMemo(scrapNotice, memo)
                    }
                )
            }
        }
    }
}