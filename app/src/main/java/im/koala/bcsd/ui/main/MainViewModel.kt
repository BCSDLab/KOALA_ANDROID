package im.koala.bcsd.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.state.Result
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.usecase.GetKeywordListUseCase
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
    private val getKeywordListUseCase: GetKeywordListUseCase
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
}
data class KeywordUi(
    val keywordList: MutableList<KeywordResponse> = mutableListOf(),
    var errorMessage: String = ""
)