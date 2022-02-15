package im.koala.bcsd.ui.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.domain.state.Result
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.usecase.GetKeywordListUseCase
import im.koala.domain.usecase.keywordadd.DeleteKeywordUseCase
import im.koala.domain.usecase.keywordadd.PushKeywordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getKeywordListUseCase: GetKeywordListUseCase,
    private val deleteKeywordUseCase: DeleteKeywordUseCase,
    private val pushKeywordUseCase: PushKeywordUseCase,
) : ViewModel() {
    private val _selectedTab: MutableState<MainScreenBottomTab> = mutableStateOf(MainScreenBottomTab.KEYWORD)
    val selectedTab: State<MainScreenBottomTab> get() = _selectedTab

    private val _keywordUi: MutableState<KeywordUi> = mutableStateOf(KeywordUi())
    val keywordUi: State<KeywordUi> get() = _keywordUi

    val keywordState: StateFlow<Result>
    val domainKeywordSharedFlow = MutableSharedFlow<Result>()

    fun selectTab(tab: MainScreenBottomTab) {
        _selectedTab.value = tab
    }
    init {

        val onGetKeywordListUseCase = domainKeywordSharedFlow.shareIn(
            viewModelScope,
            SharingStarted.Eagerly, 0
        )
        keywordState = onGetKeywordListUseCase.stateIn(viewModelScope, SharingStarted.Eagerly, Result.Uninitialized)

        viewModelScope.launch(Dispatchers.IO) {
            keywordState.collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        _keywordUi.value = _keywordUi.value.copy(keywordList = (it.data as MutableList<KeywordResponse>))
                    }
                    is Result.Fail<*> -> {
                        val response = it.data as CommonResponse
                        _keywordUi.value = _keywordUi.value.copy(errorMessage = response.errorMessage!!)
                    }
                }
            }
        }
    }

    fun executeGetKeywordList() {
        getKeywordListUseCase().onEach {
            domainKeywordSharedFlow.emit(it)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun reloadKeywordList() {
        viewModelScope.launch(Dispatchers.IO) {
            keywordState.collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        _keywordUi.value =
                            _keywordUi.value.copy(keywordList = (it.data as MutableList<KeywordResponse>))
                    }
                    is Result.Fail<*> -> {
                        val response = it.data as CommonResponse
                        _keywordUi.value =
                            _keywordUi.value.copy(errorMessage = response.errorMessage!!)
                    }
                }
            }
        }
    }

    fun pushKeyword(
        alarmCycle: Int,
        alarmMode: Boolean,
        isImportant: Boolean,
        name: String,
        untilPressOkButton: Boolean,
        vibrationMode: Boolean,
        alarmSiteList: List<String>
    ) {
        viewModelScope.launch {
            when (
                val pushKeywordResponse = pushKeywordUseCase(
                    alarmCycle,
                    alarmMode,
                    isImportant,
                    name,
                    untilPressOkButton,
                    vibrationMode,
                    alarmSiteList
                )
            ) {
                is Result.Success<*> -> {
                    Log.d("KeywordAddViewModel", pushKeywordResponse.data.toString())
                    reloadKeywordList()
                    executeGetKeywordList()
                }
                is Result.Fail<*> -> Log.d(
                    "KeywordAddViewModel",
                    pushKeywordResponse.data.toString()
                )
            }
        }
    }

    fun deleteKeyword(keyword: String) {
        viewModelScope.launch {
            when (val deleteKeywordResponse = deleteKeywordUseCase(keyword)) {
                is Result.Success<*> -> {
                    Log.d("mainViewModel", deleteKeywordResponse.data.toString())
                    reloadKeywordList()
                    executeGetKeywordList()
                    Log.d("mainViewModel", keywordUi.value.keywordList.toString())
                }
                is Result.Fail<*> -> Log.d(
                    "mainViewModel",
                    deleteKeywordResponse.data.toString()
                )
            }
        }
    }
}
data class KeywordUi(
    val keywordList: MutableList<KeywordResponse> = mutableListOf(),
    var errorMessage: String = ""
)