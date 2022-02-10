package im.koala.bcsd.ui.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.domain.state.NetworkState
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.usecase.GetKeywordListUseCase
import im.koala.domain.usecase.keyword.DeleteKeywordUseCase
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
) : ViewModel() {
    private val _selectedTab: MutableState<MainScreenBottomTab> = mutableStateOf(MainScreenBottomTab.KEYWORD)
    val selectedTab: State<MainScreenBottomTab> get() = _selectedTab

    private val _keywordUi: MutableState<KeywordUi> = mutableStateOf(KeywordUi())
    val keywordUi: State<KeywordUi> get() = _keywordUi

    val keywordState: StateFlow<NetworkState>
    val domainKeywordSharedFlow = MutableSharedFlow<NetworkState>()

    fun selectTab(tab: MainScreenBottomTab) {
        _selectedTab.value = tab
    }
    init {

        val onGetKeywordListUseCase = domainKeywordSharedFlow.shareIn(
            viewModelScope,
            SharingStarted.Eagerly, 0
        )
        keywordState = onGetKeywordListUseCase.stateIn(viewModelScope, SharingStarted.Eagerly, NetworkState.Uninitialized)

        viewModelScope.launch(Dispatchers.IO) {
            keywordState.collectLatest {
                when (it) {
                    is NetworkState.Success<*> -> {
                        _keywordUi.value = _keywordUi.value.copy(keywordList = (it.data as MutableList<KeywordResponse>))
                    }
                    is NetworkState.Fail<*> -> {
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

    fun deleteKeyword(keyword:String){
        viewModelScope.launch {
            when(val deleteKeywordResponse = deleteKeywordUseCase(keyword)){
                is NetworkState.Success<*> -> Log.d("mainViewModel",deleteKeywordResponse.data.toString())
                is NetworkState.Fail<*> -> Log.d("mainViewModel",deleteKeywordResponse.data.toString())
            }
        }
    }
}
data class KeywordUi(
    val keywordList: MutableList<KeywordResponse> = mutableListOf(),
    var errorMessage: String = ""
)