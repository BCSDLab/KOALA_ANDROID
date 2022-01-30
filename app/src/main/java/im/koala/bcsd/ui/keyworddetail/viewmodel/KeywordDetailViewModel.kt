package im.koala.bcsd.ui.keyworddetail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.ui.BaseViewModel
import im.koala.bcsd.ui.keyworddetail.state.KeywordDetailUiState
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.KeywordNoticeReadFilter
import im.koala.domain.entity.keyword.Site
import im.koala.domain.usecase.keyword.GetKeywordNoticesUseCase
import im.koala.domain.usecase.keyword.KeepSelectedKeywordNoticeUseCase
import im.koala.domain.usecase.keyword.MakeSiteTabItemUseCase
import im.koala.domain.usecase.keyword.RemoveSelectedKeywordNoticeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordDetailViewModel @Inject constructor(
    private val getKeywordNoticesUseCase: GetKeywordNoticesUseCase,
    private val makeSiteTabItemUseCase: MakeSiteTabItemUseCase,
    private val keepSelectedKeywordNoticeUseCase: KeepSelectedKeywordNoticeUseCase,
    private val removeSelectedKeywordNoticeUseCase: RemoveSelectedKeywordNoticeUseCase
) : BaseViewModel() {
    var job: Job? = null

    var keywordDetailUiState by mutableStateOf(KeywordDetailUiState())
        private set
    var search by mutableStateOf("")

    private suspend fun fetchKeywordNotices(keyword: String) {
        withLoading {
            val checkState = keywordDetailUiState.keywordNotices.map {
                it.id to it.isChecked
            }.toMap()

            val keywordNotices = getKeywordNoticesUseCase(
                keyword = keyword,
                search = search,
                site = keywordDetailUiState.selectedSite,
                keywordNoticeReadFilter = keywordDetailUiState.keywordNoticeReadFilter
            ).map { keywordNotice ->
                checkState[keywordNotice.id]?.let {
                    keywordNotice.copy(isChecked = it)
                } ?: keywordNotice
            }

            keywordDetailUiState = keywordDetailUiState.copy(
                keyword = keyword,
                keywordNotices = keywordNotices,
                keywordSiteTabs = makeSiteTabItemUseCase(
                    keywordNotices
                )
            )
        }
    }

    fun updateKeywordNotices(keyword: String) {
        job?.cancel()
        job = viewModelScope.launch(vmExceptionHandler) {
            withLoading {
                fetchKeywordNotices(keyword)
            }
        }
    }

    fun updateKeywordNotices() {
        updateKeywordNotices(keywordDetailUiState.keyword)
    }

    fun setSite(site: Site) {
        job?.cancel()
        job = viewModelScope.launch(vmExceptionHandler) {
            withLoading {
                keywordDetailUiState = keywordDetailUiState.copy(
                    selectedSite = site
                )

                fetchKeywordNotices(keyword = keywordDetailUiState.keyword)
            }
        }
    }

    fun setKeywordNoticeReadFilter(keywordNoticeReadFilter: KeywordNoticeReadFilter) {
        job?.cancel()
        job = viewModelScope.launch(vmExceptionHandler) {
            withLoading {
                keywordDetailUiState = keywordDetailUiState.copy(
                    keywordNoticeReadFilter = keywordNoticeReadFilter
                )

                fetchKeywordNotices(keyword = keywordDetailUiState.keyword)
            }
        }
    }

    fun setCheckState(items: List<KeywordNotice>, isChecked: Boolean) {
        keywordDetailUiState = keywordDetailUiState.copy(
            keywordNotices = keywordDetailUiState.keywordNotices.map {
                if (items.contains(it)) {
                    it.copy(isChecked = isChecked)
                } else {
                    it
                }
            }
        )
    }

    fun keepSelectedKeywordDetailItem(keywordNotices: List<KeywordNotice>) {
        job?.cancel()
        job = viewModelScope.launch(vmExceptionHandler) {
            withLoading {
                keepSelectedKeywordNoticeUseCase(
                    keywordNotices
                )

                fetchKeywordNotices(keyword = keywordDetailUiState.keyword)
            }
        }
    }

    fun removeSelectedKeywordDetailItem(keywordNotices: List<KeywordNotice>) {
        job?.cancel()
        job = viewModelScope.launch(vmExceptionHandler) {
            withLoading {
                removeSelectedKeywordNoticeUseCase(
                    keywordNotices
                )
                fetchKeywordNotices(keyword = keywordDetailUiState.keyword)
            }
        }
    }
}