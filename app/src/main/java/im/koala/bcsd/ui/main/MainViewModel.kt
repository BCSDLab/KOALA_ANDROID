package im.koala.bcsd.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.state.NetworkState
import im.koala.domain.usecase.GetKeywordListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getKeywordListUseCase: GetKeywordListUseCase
) : ViewModel() {
    private val _selectedTab: MutableState<MainScreenBottomTab> = mutableStateOf(MainScreenBottomTab.KEYWORD)
    val selectedTab: State<MainScreenBottomTab> get() = _selectedTab

    private val _keywordState = MutableLiveData<NetworkState>(NetworkState.Uninitialized)
    val keywordState: LiveData<NetworkState> get() = _keywordState

    fun selectTab(tab: MainScreenBottomTab) {
        _selectedTab.value = tab
    }

    fun executeGetKeywordList() {
        _keywordState.value = NetworkState.Loading
        viewModelScope.launch {
            getKeywordListUseCase(
                onSuccess = {
                    _keywordState.value = NetworkState.Success(it)
                },
                onFail = {
                    _keywordState.value = NetworkState.Fail(it)
                }
            )
        }
    }
}