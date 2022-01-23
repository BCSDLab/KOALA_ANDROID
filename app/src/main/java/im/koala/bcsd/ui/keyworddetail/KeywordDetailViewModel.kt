package im.koala.bcsd.ui.keyworddetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.domain.entity.keyword.KeywordItemReadFilter
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.KeywordListItemFilter
import im.koala.domain.entity.keyword.Site
import im.koala.domain.usecase.keyword.GetKeywordItemListUseCase
import im.koala.domain.usecase.keyword.MakeSiteTabItemUseCase
import javax.inject.Inject

@HiltViewModel
class KeywordDetailViewModel @Inject constructor(
    private val getKeywordItemListUseCase: GetKeywordItemListUseCase,
    private val makeSiteTabItemUseCase: MakeSiteTabItemUseCase
) : ViewModel() {
    var keyword by mutableStateOf("")
        private set

    var filterState by mutableStateOf(KeywordListItemFilter.default())
        private set

    private var _keywordListItems = mutableStateListOf<KeywordNotice>()
    val keywordNotices: List<KeywordNotice> get() = _keywordListItems

    private var _keywordTabs = mutableStateMapOf<Site, String>()
    val keywordTabs: Map<Site, String> get() = _keywordTabs

    private var _selectedAll by mutableStateOf(false)
    var selectedAll: Boolean
        set(value) {
            for (i in keywordNotices.indices) {
                setCheckState(keywordNotices[i], value)
            }
        }
        get() {
            _selectedAll =
                keywordNotices.isNotEmpty() && keywordNotices.find { !it.isChecked } == null
            return _selectedAll
        }

    fun fetchDetailListItems(keyword: String) {
        this.keyword = keyword

        _keywordListItems.removeAll { true }
        _keywordTabs.clear()

        _keywordListItems.addAll(getKeywordItemListUseCase(keyword, filterState))
        _keywordTabs.putAll(makeSiteTabItemUseCase(keywordNotices))
    }

    fun setSearchFilter(search: String) {
        filterState = filterState.copy(search = search)
    }

    fun setSiteFilter(site: Site) {
        filterState = filterState.copy(site = site)
    }

    fun setKeywordItemReadFilter(keywordItemReadFilter: KeywordItemReadFilter) {
        filterState = filterState.copy(keywordItemReadFilter = keywordItemReadFilter)
    }

    fun setCheckState(item: KeywordNotice, isChecked: Boolean) {
        val index = _keywordListItems.indexOf(item)
        _keywordListItems[index] = _keywordListItems[index].copy(isChecked = isChecked)
    }

    fun keepSelectedKeywordDetailItem() {

    }

    fun removeSelectedKeywordDetailItem() {

    }
}