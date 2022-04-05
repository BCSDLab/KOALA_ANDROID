package im.koala.bcsd.ui.history

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.domain.entity.history.HistoryNotice
import im.koala.domain.entity.history.ScrapNotice
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
    private val getScrapWithMemoUseCase: GetScrapWithMemoUseCase,
    private val postMemoUseCase: PostMemoUseCase,
    private val patchMemoUseCase: PatchMemoUseCase
) : ViewModel() {
    var historyUiState by mutableStateOf(HistoryUiState())
    var storageUiState by mutableStateOf(StorageUiState())

    private suspend fun getHistory(isRead: Boolean? = null) {
        val history: List<HistoryNotice> = if (isRead == null) {
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
        setHistoryCheckedList()
    }

    fun setHistoryCheckState(items: List<HistoryNotice>, isChecked: Boolean) {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                if (items.contains(it)) {
                    it.copy(isChecked = isChecked)
                } else {
                    it
                }
            }
        )
        setHistoryCheckedList()
    }

    fun historyReadCheck() {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                if (it.isRead) {
                    it.copy(isChecked = true)
                } else {
                    it
                }
            }
        )
        setHistoryCheckedList()
    }

    fun historyUnreadCheck() {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                if (it.isRead) {
                    it
                } else {
                    it.copy(isChecked = true)
                }
            }
        )
        setHistoryCheckedList()
    }

    fun historyAllCheck(isChecked: Boolean) {
        historyUiState = historyUiState.copy(
            history = historyUiState.history.map {
                it.copy(isChecked = isChecked)
            }
        )
        setHistoryCheckedList()
    }

    fun deleteHistory(noticeId: List<Int>) {
        viewModelScope.launch {
            when (val response = deleteHistoryUseCase(noticeId)) {
                is Result.Success<*> -> {
                    historyUiState = historyUiState.copy(
                        snackBarState = SnackBarState.ShowSnackBar(
                            snackBarMessage = response.data.toString(),
                            snackBarCommend = SnackBarCommend.DeleteHistory,
                            isSuccess = true
                        ),
                        deletedHistoryId = historyUiState.checkedHistoryList.map {
                            it.id
                        }
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
        setHistoryCheckedList()
    }

    fun undoDeleteHistory(noticeId: List<Int>) {
        viewModelScope.launch {
            when (val response = undoDeleteHistoryUseCase(noticeId)) {
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
        setHistoryCheckedList()
    }

    fun setSnackBarStateNone() {
        historyUiState = historyUiState.copy(
            snackBarState = SnackBarState.NoneSnackBar
        )
    }

    private fun setHistoryCheckedList() {
        val checkedIdList = mutableListOf<HistoryNotice>()
        for (history in historyUiState.history) {
            if (history.isChecked) checkedIdList.add(history)
        }
        historyUiState = historyUiState.copy(
            checkedHistoryList = checkedIdList.toList()
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
                if (it.isEmpty()) {
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
            when (val response = deleteScrapUseCase(crawlingIdList)) {
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

    fun setStorageCheckState(items: List<ScrapNotice>, isChecked: Boolean) {
        storageUiState = storageUiState.copy(
            scrapNotice = storageUiState.scrapNotice.map {
                if (items.contains(it)) {
                    it.copy(isChecked = isChecked)
                } else {
                    it
                }
            }
        )
        setStorageCheckedList()
    }

    private fun setStorageCheckedList() {
        val checkedIdList = mutableListOf<ScrapNotice>()
        for (scrapNotice in storageUiState.scrapNotice) {
            if (scrapNotice.isChecked) checkedIdList.add(scrapNotice)
        }
        storageUiState = storageUiState.copy(
            checkedScrapNotice = checkedIdList.toList()
        )
    }

    fun updateStorage() {
        viewModelScope.launch {
            storageUiState = storageUiState.copy(
                scrapNotice = getScrapWithMemoUseCase()
            )
        }
        setStorageCheckedList()
    }

    fun storageAllCheck(isChecked: Boolean) {
        storageUiState = storageUiState.copy(
            scrapNotice = storageUiState.scrapNotice.map {
                it.copy(
                    isChecked = isChecked
                )
            }
        )
        setStorageCheckedList()
    }

    fun deleteScrapNotice() {
        viewModelScope.launch {
            when (val response = deleteScrapUseCase(
                storageUiState.checkedScrapNotice.map {
                    it.id
                }
            )) {
                is Result.Success<*> -> {
                    updateStorage()
                }
                is Result.Fail<*> -> {
                    Log.e("Delete Fail", response.data.toString())
                }
            }
        }
    }

    fun editMemo(scrapNotice: ScrapNotice, memo: String) {
        viewModelScope.launch {
            when (val response = if (scrapNotice.memo == null) {
                postMemoUseCase(scrapNotice.userScrapedId, memo)
            } else {
                patchMemoUseCase(scrapNotice.userScrapedId, memo)
            }) {
                is Result.Success<*> -> {
                    updateStorage()
                }
                is Result.Fail<*> -> {
                    Log.e("Memo Fail", response.data.toString())
                }
            }
        }
    }
}


data class HistoryUiState(
    val history: List<HistoryNotice> = listOf(),
    val checkedHistoryList: List<HistoryNotice> = listOf(),
    val deletedHistoryId: List<Int> = listOf(),
    val scrapedHistoryId: List<Int> = listOf(),
    val snackBarState: SnackBarState = SnackBarState.NoneSnackBar
)

data class StorageUiState(
    val scrapNotice: List<ScrapNotice> = listOf(),
    val checkedScrapNotice: List<ScrapNotice> = listOf()
)