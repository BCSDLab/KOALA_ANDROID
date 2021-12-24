package im.koala.bcsd.ui.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _selectedTab: MutableState<MainScreenBottomTab> = mutableStateOf(MainScreenBottomTab.KEYWORD)
    val selectedTab: State<MainScreenBottomTab> get() = _selectedTab

    fun selectTab(tab: MainScreenBottomTab) {
        _selectedTab.value = tab
    }
}