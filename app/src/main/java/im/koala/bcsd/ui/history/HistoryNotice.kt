package im.koala.bcsd.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaCheckBox

@Composable
fun HistoryNoticeScreen(
    modifier: Modifier = Modifier,
    historyViewModel: HistoryViewModel
) {
    val isPopupMenuExpanded = remember { mutableStateOf(false) }
    val isAllChecked = remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

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
                checked = isAllChecked.value,
                onCheckedChange = {
                    isAllChecked.value = it
                    historyViewModel.manageAllCheckState(it)
                }
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
                    modifier = modifier,
                    iconPainter = painterResource(R.drawable.ic_inbox_in),
                    text = stringResource(R.string.history_keep),
                    enabled = historyViewModel.historyUiState.checkedHistoryId.isNotEmpty(),
                    onClick = {
                        historyViewModel.scrapHistory(
                            historyViewModel.historyUiState.checkedHistoryId
                        )
                    },
                )
                HistoryCommandButton(
                    modifier = modifier
                        .padding(start = 4.dp),
                    iconPainter = painterResource(id = R.drawable.ic_trash),
                    text = stringResource(id = R.string.history_delete),
                    enabled = historyViewModel.historyUiState.checkedHistoryId.isNotEmpty(),
                    onClick = {
                        historyViewModel.deleteHistory(
                            historyViewModel.historyUiState.checkedHistoryId
                        )
                    }
                )
                IconButton(
                    modifier = modifier
                        .padding(end = 8.dp),
                    onClick = { isPopupMenuExpanded.value = !isPopupMenuExpanded.value }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dots_vertical),
                        contentDescription = ""
                    )
                    HistoryCheckFilterMenu(
                        expanded = isPopupMenuExpanded.value,
                        onDismissRequest = { isPopupMenuExpanded.value = false },
                        modifier = modifier,
                        onClickReadHistory = { historyViewModel.readCheck() },
                        onClickUnreadHistory = { historyViewModel.unreadCheck() }
                    )
                }
            }
        }
        LazyColumn {
            items(
                items = historyViewModel.historyUiState.history,
                key = { it.id }
            ) { history ->
                HistoryItem(
                    modifier = modifier,
                    history = history,
                    onCheckedChange = {
                        historyViewModel.setCheckState(listOf(history), it)
                    }
                )
            }
        }
    }
    when (val snackBarState = historyViewModel.historyUiState.snackBarState) {
        is SnackBarState.ShowSnackBar -> {
            val snackBarActionString = stringResource(id =R.string.history_undo)
            val scrapSnackBarMessage = stringResource(id = R.string.history_scrap_message)
            val undoScrapSuccessMessage = stringResource(id = R.string.history_undo_scrap_success_message)
            val undoScrapFailMessage = stringResource(id = R.string.history_undo_scrap_fail_message)
            LaunchedEffect(historyViewModel.historyUiState.snackBarState) {
                snackBarHostState.showSnackbar(
                    message = when (snackBarState.snackBarCommend) {
                        is SnackBarCommend.ScrapHistory -> {
                            snackBarState.snackBarMessage + scrapSnackBarMessage
                        }
                        is SnackBarCommend.UndoScrapHistory -> {
                            if (snackBarState.isSuccess) undoScrapSuccessMessage
                            else undoScrapFailMessage
                        }
                        else -> snackBarState.snackBarMessage
                                                                                         },
                    actionLabel = if ((snackBarState.snackBarCommend == SnackBarCommend.DeleteHistory || snackBarState.snackBarCommend == SnackBarCommend.ScrapHistory) && snackBarState.isSuccess) {
                        snackBarActionString
                    } else {
                        null
                    },
                    duration = SnackbarDuration.Short
                ).let {
                    when (it) {
                        SnackbarResult.ActionPerformed -> {
                            historyViewModel.setSnackBarStateNone()
                            when (snackBarState.snackBarCommend) {
                                is SnackBarCommend.DeleteHistory -> if (snackBarState.isSuccess) historyViewModel.undoDeleteHistory(historyViewModel.historyUiState.deletedHistoryId)
                                is SnackBarCommend.ScrapHistory -> if (snackBarState.isSuccess) historyViewModel.undoScrapHistory(historyViewModel.historyUiState.scrapedHistoryId)
                                else -> { }
                            }
                        }
                        SnackbarResult.Dismissed -> {
                            historyViewModel.setSnackBarStateNone()
                            if (snackBarState.snackBarCommend == SnackBarCommend.DeleteHistory) {
                                historyViewModel.emptyDeletedHistoryIdList()
                            } else if (snackBarState.snackBarCommend == SnackBarCommend.ScrapHistory) {
                                historyViewModel.emptyScrapedHistoryIdList()
                            }
                        }
                    }
                }
            }
        }
    }
}