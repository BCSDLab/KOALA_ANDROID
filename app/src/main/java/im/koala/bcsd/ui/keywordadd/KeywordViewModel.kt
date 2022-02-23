package im.koala.bcsd.ui.keywordadd

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import im.koala.domain.state.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.navigation.NavScreen
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
import kotlinx.coroutines.delay
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
    private val getKeywordDetailsUseCase: GeyKeywordDetailsUseCase,
) : ViewModel() {
    var uiState by mutableStateOf(KeywordAddUiState())
        private set

    /*
        getKeywordSearchList(keyword:String),getSiteSearchList(site:String)
        : 파라미터로 들어온 값에 해당하는 리스트를 livedata 에 저장한다.
    */
    fun getKeywordSearchList(keyword: String) {
        viewModelScope.launch {
            when (val _keywordSearchList = getKeywordSearchUseCase(keyword)) {
                is Result.Success<*> -> uiState =
                    uiState.copy(searchList = _keywordSearchList.data as List<String>)
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
                is Result.Success<*> -> uiState =
                    uiState.copy(searchList = _keywordSiteSearchList.data as List<String>)
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
                is Result.Success<*> -> uiState =
                    uiState.copy(recommendationList = _keywordSiteRecommendationList.data as List<String>)
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
                is Result.Success<*> -> uiState =
                    uiState.copy(recommendationList = _keywordRecommendationList.data as List<String>)
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
            uiState.recentSearchList.let { setRecentKeywordSearchList.addAll(it) }
            if (keywordSearch !in setRecentKeywordSearchList) setRecentKeywordSearchList.add(
                keywordSearch
            )
            setRecentSearchListUseCase("keyword_search_key", setRecentKeywordSearchList)
        }
    }

    fun getRecentKeywordSearchList() {
        viewModelScope.launch {
            getRecentSearchListUseCase("keyword_search_key").collectLatest { value ->
                uiState = uiState.copy(recentSearchList = value)
            }
        }
    }

    fun setRecentSiteSearchList(siteSearch: String) {
        val setRecentSiteSearchList = mutableListOf<String>()
        viewModelScope.launch {
            uiState.recentSearchList.let { setRecentSiteSearchList.addAll(it) }
            if (siteSearch !in setRecentSiteSearchList) setRecentSiteSearchList.add(siteSearch)
            setRecentSearchListUseCase("site_search_key", setRecentSiteSearchList)
        }
    }

    fun getRecentSiteSearchList() {
        viewModelScope.launch {
            getRecentSearchListUseCase("site_search_key").collectLatest { value ->
                uiState = uiState.copy(recentSearchList = value)
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
        uiState = uiState.copy(siteList = _alarmSiteList)
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
        getKeywordNameListToEdit() : 키워드 수정하기를 할 때, 중복검사를 하기 위해서 키워드 name 만 담긴 리스트를 가져오는 메서드
    */

    fun getKeywordNameList() {
        viewModelScope.launch {
            getKeywordListUseCase().collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        val keywordList = it.data as List<KeywordResponse>
                        val keywordNameList: MutableList<String> = mutableListOf()
                        keywordList.forEach { data -> keywordNameList.add(data.name) }
                        uiState = uiState.copy(nameList = keywordNameList)
                    }
                    is Result.Fail<*> -> Log.d("KeywordAddViewModel", it.data.toString())
                }
            }
        }
    }

    fun getKeywordNameListToEdit(keyword: String) {
        viewModelScope.launch {
            getKeywordListUseCase().collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        val keywordList = it.data as List<KeywordResponse>
                        val keywordNameList: MutableList<String> = mutableListOf()
                        keywordList.forEach { data -> keywordNameList.add(data.name) }
                        keywordNameList.remove(keyword)
                        uiState = uiState.copy(nameList = keywordNameList)
                    }
                    is Result.Fail<*> -> Log.d("KeywordAddViewModel", it.data.toString())
                }
            }
        }
    }

    /*
         changeState(commend: String, stringData: String = "", intData: Int = 0)
         키워드 추가할 때, uiState 를 변경할 수 있는 setter
    */
    fun changeState(commend: String, stringData: String = "", intData: Int = 0) {
        when (commend) {
            "keyword" -> uiState = uiState.copy(keyword = stringData)
            "site" -> uiState = uiState.copy(site = stringData)
            "alarmCycle" -> uiState = uiState.copy(alarmCycle = intData)
            "isImportant" -> uiState = uiState.copy(isImportant = true)
            "isNotImportant" -> uiState = uiState.copy(isImportant = false)
            "silentMode" -> uiState = uiState.copy(silentMode = !uiState.silentMode)
            "vibrationMode" -> uiState = uiState.copy(vibrationMode = !uiState.vibrationMode)
            "untilPressOkButton" -> uiState =
                uiState.copy(untilPressOkButton = !uiState.untilPressOkButton)
        }
    }

    /*
        initState() : 키워드 추가할 때 필요한 변수를 초기화 한다.
    */

    fun initState() {
        uiState = uiState.copy(isImportant = true)
        uiState = uiState.copy(vibrationMode = true)
        uiState = uiState.copy(untilPressOkButton = true)
        uiState = uiState.copy(silentMode = true)
        uiState = uiState.copy(alarmCycle = 0)
        deleteAllSiteList()
        getKeywordNameList()
        changeState("keyword")
    }

    /*
        getKeywordDetails(keyword: String, navController:NavController)
        navController 를 통해 수정화면으로 전환한 후, delay 후 수신한 response 를 uiState 와 매핑한다.
        delay 를 안하면 수정화면 세팅이 안된다.
    */

    fun getKeywordDetails(keyword: String, navController:NavController) {
        viewModelScope.launch {
            when (val _keywordDetails = getKeywordDetailsUseCase(keyword)) {
                is Result.Success<*> -> {
                    val keywordDetails: KeywordAddResponseUi =
                        _keywordDetails.data as KeywordAddResponseUi
                    Log.d(
                        "KeywordAddViewModel",
                        "keywordDetails: ${_keywordDetails.data.toString()}"
                    )
                    navController.navigate(NavScreen.KeywordEdit.route)
                    delay(500)
                    changeState("keyword", stringData = keyword)
                    changeState("alarmCycle", intData = keywordDetails.alarmCycle)
                    uiState = uiState.copy(isImportant = keywordDetails.isImportant)
                    uiState = uiState.copy(vibrationMode = keywordDetails.vibrationMode)
                    uiState = uiState.copy(untilPressOkButton = keywordDetails.untilPressOkButton)
                    uiState = uiState.copy(silentMode = keywordDetails.silentMode)
                    uiState = uiState.copy(siteList = keywordDetails.siteList)
                    uiState = uiState.copy(editKeyword = keywordDetails.name)
                    keywordDetails.siteList.onEach { addAlarmSiteList(it) }
                    getKeywordNameListToEdit(keyword)
                }
                is Result.Fail<*> -> Log.d(
                    "KeywordAddViewModel",
                    "keywordDetails: ${_keywordDetails.data.toString()}"
                )
            }
        }
    }
}