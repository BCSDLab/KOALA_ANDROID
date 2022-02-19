package im.koala.bcsd.ui.keywordadd

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import im.koala.domain.state.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.data.api.response.keywordadd.KeywordAddResponse
import im.koala.data.api.response.keywordadd.KeywordAddResponseUi
import im.koala.data.repository.local.AlarmSiteDataSource
import im.koala.data.repository.local.AlarmSite
import im.koala.domain.model.KeywordResponse
import im.koala.domain.usecase.GetKeywordListUseCase
import im.koala.domain.usecase.keywordadd.GetKeywordRecommendationUseCase
import im.koala.domain.usecase.keywordadd.GetKeywordSearchUseCase
import im.koala.domain.usecase.keywordadd.GetRecentSearchListUseCase
import im.koala.domain.usecase.keywordadd.GetSiteRecommendationUseCase
import im.koala.domain.usecase.keywordadd.GetSiteSearchUseCase
import im.koala.domain.usecase.keywordadd.GeyKeywordDetailsUseCase
import im.koala.domain.usecase.keywordadd.SetRecentSearchListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest

@HiltViewModel
class KeywordViewModel @Inject constructor(
    private val getKeywordRecommendationUseCase: GetKeywordRecommendationUseCase,
    private val getKeywordSearchUseCase: GetKeywordSearchUseCase,
    private val getSiteRecommendationUseCase: GetSiteRecommendationUseCase,
    private val getSiteSearchUseCase: GetSiteSearchUseCase,
    private val getRecentSearchListUseCase: GetRecentSearchListUseCase,
    private val setRecentSearchListUseCase: SetRecentSearchListUseCase,
    private val getKeywordListUseCase: GetKeywordListUseCase,
    private val alarmSiteData: AlarmSiteDataSource,
    private val getKeywordDetailsUseCase: GeyKeywordDetailsUseCase
) : ViewModel() {
    val keywordSearchList: MutableLiveData<List<String>> = MutableLiveData()
    val keywordRecommendationList: MutableLiveData<List<String>> = MutableLiveData()
    val recentKeywordSearchList: MutableLiveData<List<String>> = MutableLiveData(listOf(""))
    val siteSearchList: MutableLiveData<List<String>> = MutableLiveData()
    val siteRecommendationList: MutableLiveData<List<String>> = MutableLiveData()
    val recentSiteSearchList: MutableLiveData<List<String>> = MutableLiveData(listOf(""))
    val alarmSiteList: MutableLiveData<List<String>> = MutableLiveData(listOf(""))
    val keywordNameList: MutableLiveData<List<String>> = MutableLiveData(listOf(""))

    /*
        키워드 수정하기 변수
    */
    val uiState: MutableLiveData<KeywordAddResponseUi> = MutableLiveData()
//    val isImportantState: MutableLiveData<Boolean> = MutableLiveData()
//    val silentModeState: MutableLiveData<Boolean> = MutableLiveData()
//    val untilPressOkButtonState: MutableLiveData<Boolean> = MutableLiveData()
//    val vibrationModeState: MutableLiveData<Boolean> = MutableLiveData()
//    val keywordSearch: MutableLiveData<String> = MutableLiveData()
//    val alarmCycleData: MutableLiveData<Int> = MutableLiveData()

    /*
        getKeywordSearchList(keyword:String),getSiteSearchList(site:String)
        : 파라미터로 들어온 값에 해당하는 리스트를 livedata 에 저장한다.
    */
    fun getKeywordSearchList(keyword: String) {
        viewModelScope.launch {
            when (val _keywordSearchList = getKeywordSearchUseCase(keyword)) {
                is Result.Success<*> ->
                    keywordSearchList.value =
                        _keywordSearchList.data as List<String>
                is Result.Fail<*> -> Log.d(
                    "KeywordAddViewModel",
                    _keywordSearchList.data.toString()
                )
            }
        }
    }

    fun getSiteSearchList(site: String) {
        viewModelScope.launch {
            when (val _keywordSiteSearchList = getSiteSearchUseCase(site)) {
                is Result.Success<*> ->
                    siteSearchList.value =
                        _keywordSiteSearchList.data as List<String>
                is Result.Fail<*> -> Log.d(
                    "KeywordAddViewModel",
                    _keywordSiteSearchList.data.toString()
                )
            }
        }
    }

    /*
         getSiteRecommendation(),getKeywordRecommendation()
         : 추천 키워드, 추천 대상이 담긴 리스트를 서버에서 받아와서 livedata 에 저장한다.
   */

    fun getSiteRecommendation() {
        viewModelScope.launch {
            when (val _keywordSiteRecommendationList = getSiteRecommendationUseCase()) {
                is Result.Success<*> ->
                    siteRecommendationList.value =
                        _keywordSiteRecommendationList.data as List<String>
                is Result.Fail<*> -> Log.d(
                    "KeywordAddViewModel",
                    _keywordSiteRecommendationList.data.toString()
                )
            }
        }
    }

    fun getKeywordRecommendation() {
        viewModelScope.launch {
            when (val _keywordRecommendationList = getKeywordRecommendationUseCase()) {
                is Result.Success<*> ->
                    keywordRecommendationList.value =
                        _keywordRecommendationList.data as List<String>
                is Result.Fail<*> -> Log.d(
                    "KeywordAddViewModel",
                    _keywordRecommendationList.data.toString()
                )
            }
        }
    }
    /*
        setRecentKeywordSearchList(keywordSearch:String), setRecentSiteSearchList(siteSearch:String)
        : 최근에 검색했던 키워드, 알림받을 대상 리스트를 추가하는 메서드
        getRecentKeywordSearchList(), getRecentSiteSearchList()
        : 최근에 검색했던 키워드, 알림받을 대상 리스트를 가져오는 메서드
    */

    fun setRecentKeywordSearchList(keywordSearch: String) {
        val setRecentKeywordSearchList = mutableListOf<String>()
        viewModelScope.launch {
            recentKeywordSearchList.value?.let { setRecentKeywordSearchList.addAll(it) }
            if (keywordSearch !in setRecentKeywordSearchList) setRecentKeywordSearchList.add(
                keywordSearch
            )
            setRecentSearchListUseCase("keyword_search_key", setRecentKeywordSearchList)
        }
    }

    fun getRecentKeywordSearchList() {
        viewModelScope.launch {
            getRecentSearchListUseCase("keyword_search_key").collectLatest { value ->
                recentKeywordSearchList.value = value
            }
        }
    }

    fun setRecentSiteSearchList(siteSearch: String) {
        val setRecentSiteSearchList = mutableListOf<String>()
        viewModelScope.launch {
            recentSiteSearchList.value?.let { setRecentSiteSearchList.addAll(it) }
            if (siteSearch !in setRecentSiteSearchList) setRecentSiteSearchList.add(siteSearch)
            setRecentSearchListUseCase("site_search_key", setRecentSiteSearchList)
        }
    }

    fun getRecentSiteSearchList() {
        viewModelScope.launch {
            getRecentSearchListUseCase("site_search_key").collectLatest { value ->
                recentSiteSearchList.value = value
            }
        }
    }

    /*
         getAlarmSiteList() : 알림받을 대상 리스트를 가져오는 메서드
         addAlarmSiteList(site:String) : 알림받을 대상 리스트를 추가하는 메서드
         deleteAlarmSite(site:String) : 알림받을 대상 리스트에서 파라미터로 들어온 값을 제거하는 메서드
         deleteAllSiteList() : 알림받을 대상 리스트 내에 있는 값들을 전부 없애는 메서드
    */

    fun getAlarmSiteList() {
        val _alarmSiteList = mutableListOf<String>()
        alarmSiteData.getAllList { list: List<AlarmSite> ->
            list.onEach { site ->
                _alarmSiteList.add(site.site!!)
            }
        }
        alarmSiteList.value = _alarmSiteList
    }

    fun addAlarmSiteList(site: String) {
        alarmSiteData.addSite(site)
        getAlarmSiteList()
    }

    fun deleteAlarmSite(site: String) {
        alarmSiteData.deleteSite(site)
        getAlarmSiteList()
    }

    fun deleteAllSiteList() {
        alarmSiteData.deleteAllList()
        getAlarmSiteList()
    }

    /*
        getKeywordNameList() : 키워드를 추가하기 전에 키워드 중복검사를 하기위해서 키워드 name 만 담긴 리스트를 가져오는 메서드
    */

    fun getKeywordNameList() {
        viewModelScope.launch {
            getKeywordListUseCase().collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        val keywordList = it.data as List<KeywordResponse>
                        val _keywordNameList: MutableList<String> = mutableListOf()
                        keywordList.forEach { _keywordNameList.add(it.name) }
                        keywordNameList.value = _keywordNameList
                    }
                    is Result.Fail<*> -> Log.d("KeywordAddViewModel", it.data.toString())
                }
            }
        }
    }

    fun getKeywordDetails(keyword: String) {
        viewModelScope.launch {
            when (val _keywordDetails = getKeywordDetailsUseCase(keyword)) {
                is Result.Success<*> -> {
                    val keywordDetails: KeywordAddResponseUi = _keywordDetails.data as KeywordAddResponseUi
                    uiState.value = keywordDetails
//                    isImportantState.value = keywordDetails.isImportant
//                    silentModeState.value = keywordDetails.silentMode
//                    untilPressOkButtonState.value = keywordDetails.untilPressOkButton
//                    vibrationModeState.value = keywordDetails.vibrationMode
//                    keywordSearch.value = keywordDetails.name
//                    alarmCycleData.value = keywordDetails.alarmCycle
//                    siteSearchList.value = keywordDetails.siteList
                }
                is Result.Fail<*> -> Log.d(
                    "KeywordAddViewModel",
                    _keywordDetails.data.toString()
                )
            }
        }
    }
}