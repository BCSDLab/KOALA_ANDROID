package im.koala.bcsd.ui.history

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.data.api.response.ResponseWrapper
import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.state.Result
import im.koala.domain.usecase.history.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val undoDeleteHistoryUseCase: UndoDeleteHistoryUseCase,
    private val scrapHistoryUseCase: ScrapHistoryUseCase,
    private val deleteScrapUseCase: DeleteScrapUseCase,
    private val getScrapWithMemoUseCase: GetScrapWithMemoUseCase
) : ViewModel() {
    var historyUiState by mutableStateOf(HistoryUiState())

    private suspend fun getHistory(isRead: Boolean? = null) {
        val history: List<KeywordNotice> = if (isRead == null) {
            getHistoryUseCase()
        } else {
            getHistoryUseCase(isRead)
        }
        historyUiState = historyUiState.copy(
            history = history
        )
    }

    fun updateHistory(isRead: Boolean? = null) {
        viewModelScope.launch {
            getHistory(isRead)
        }
        setCheckedList()
    }

    fun setCheckState(items: List<KeywordNotice>, isChecked: Boolean) {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                if (items.contains(it)) {
                    it.copy(isChecked = isChecked)
                } else {
                    it
                }
            }
        )
        setCheckedList()
    }

    fun readCheck() {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                if (it.isRead) {
                    it.copy(isChecked = true)
                } else {
                    it
                }
            }
        )
        setCheckedList()
    }

    fun unreadCheck() {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                if (it.isRead) {
                    it
                } else {
                    it.copy(isChecked = true)
                }
            }
        )
        setCheckedList()
    }

    fun manageAllCheckState(isChecked: Boolean) {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                it.copy(isChecked = isChecked)
            }
        )
        setCheckedList()
    }

    fun deleteHistory(noticeId: List<Int>) {
        viewModelScope.launch {
            when(val response = deleteHistoryUseCase(noticeId)) {
                is Result.Success<*> -> {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = response.data.toString(),
                            snackBarCommend = SnackBarCommend.DeleteHistory,
                            isSuccess = true
                        ),
                        deletedHistoryId = historyUiState.checkedHistoryId
                    )
                    updateHistory()
                }
                is Result.Fail<*> -> {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = response.data.toString(),
                            snackBarCommend = SnackBarCommend.DeleteHistory,
                            isSuccess = false
                        )
                    )
                }
            }
        }
        setCheckedList()
    }

    fun undoDeleteHistory(noticeId: List<Int>) {
        viewModelScope.launch {
            when(val response = undoDeleteHistoryUseCase(noticeId)) {
                is Result.Success<*> -> {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = response.data.toString(),
                            snackBarCommend = SnackBarCommend.UndoDeleteHistory,
                            isSuccess = true
                        ),
                        deletedHistoryId = listOf()
                    )
                    updateHistory()
                }
                is Result.Fail<*> -> {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = response.data.toString(),
                            snackBarCommend = SnackBarCommend.DeleteHistory,
                            isSuccess = false
                        )
                    )
                }
            }
        }
        setCheckedList()
    }

    fun setSnackBarStateNone() {
        historyUiState = historyUiState.copy(
            snackBarState = SnackBarState.NoneSnackBar
        )
    }

    private fun setCheckedList() {
        val checkedIdList = mutableListOf<Int>()
        for(history in historyUiState.history) {
            checkedIdList.add(history.id)
        }
        historyUiState = historyUiState.copy(
            checkedHistoryId = checkedIdList.toList()
        )
    }

    fun emptyDeletedHistoryIdList() {
        historyUiState = historyUiState.copy(
            deletedHistoryId = listOf()
        )
    }

    fun scrapHistory(crawlingIdList: List<Int>) {
        viewModelScope.launch {
            scrapHistoryUseCase(crawlingIdList).let {
                if(it.isEmpty()) {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = it.size.toString(),
                            snackBarCommend = SnackBarCommend.ScrapHistory,
                            isSuccess = false
                        )
                    )
                } else {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = it.size.toString(),
                            snackBarCommend = SnackBarCommend.ScrapHistory,
                            isSuccess = true
                        ),
                        scrapedHistoryId = it
                    )
                    updateHistory()
                }
            }
        }
    }

    fun undoScrapHistory(crawlingIdList: List<Int>) {
        viewModelScope.launch {
            when(val response = deleteScrapUseCase(crawlingIdList)) {
                is Result.Success<*> -> {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = response.data.toString(),
                            snackBarCommend = SnackBarCommend.UndoScrapHistory,
                            isSuccess = true
                        ),
                        scrapedHistoryId = listOf()
                    )
                    updateHistory()
                }
                is Result.Fail<*> -> {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = response.data.toString(),
                            snackBarCommend = SnackBarCommend.UndoScrapHistory,
                            isSuccess = false
                        )
                    )
                }
            }
        }
    }
    fun emptyScrapedHistoryIdList() {
        historyUiState = historyUiState.copy(
            scrapedHistoryId = listOf()
        )
    }
}


data class HistoryUiState(
    val history: List<KeywordNotice> = listOf(),
    val checkedHistoryId: List<Int> = listOf(),
    val deletedHistoryId: List<Int> = listOf(),
    val scrapedHistoryId: List<Int> = listOf(),
    val snackBarState: SnackBarState = SnackBarState.NoneSnackBar
)

data class StorageUiState(
    val scrapNotice: List<ScrapNotice> = listOf()
)