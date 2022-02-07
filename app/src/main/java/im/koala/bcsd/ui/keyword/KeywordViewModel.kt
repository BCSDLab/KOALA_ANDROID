package im.koala.bcsd.ui.keyword

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import im.koala.domain.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.repository.local.AlarmSiteDataSource
import im.koala.data.repository.local.Site
import im.koala.domain.model.KeywordAddResponse
import im.koala.domain.usecase.keyword.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    private val getKeywordRecommendationUseCase: GetKeywordRecommendationUseCase,
    private val getKeywordSearchUseCase: GetKeywordSearchUseCase,
    private val getSiteRecommendationUseCase: GetSiteRecommendationUseCase,
    private val getSiteSearchUseCase: GetSiteSearchUseCase,
    private val getRecentSearchListUseCase: GetRecentSearchListUseCase,
    private val setRecentSearchListUseCase: SetRecentSearchListUseCase,
    private val pushKeywordUseCase:PushKeywordUseCase,
    private val alarmSiteData:AlarmSiteDataSource
): ViewModel(){
    val pushKeywordResponse: MutableLiveData<Response<ResponseWrapper<String>>> = MutableLiveData()

    val keywordSearchList: MutableLiveData<List<String>> = MutableLiveData()
    val keywordSiteSearchList: MutableLiveData<List<String>> = MutableLiveData()
    val keywordSiteRecommendationList: MutableLiveData<List<String>> = MutableLiveData()
    val keywordRecommendationList: MutableLiveData<List<String>> = MutableLiveData()

    val recentKeywordSearchList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))
    val recentSiteSearchList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))

    val alarmSiteList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))

    fun getKeywordSearchList(keyword:String){
        viewModelScope.launch {
            when(val _keywordSearchList = getKeywordSearchUseCase(keyword)){
                is NetworkState.Success<*> -> keywordSearchList.value = _keywordSearchList.data as List<String>
                is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",_keywordSearchList.data.toString())
            }
        }
    }

    fun getKeywordSiteSearch(site:String){
        viewModelScope.launch {
            when(val _keywordSiteSearchList = getSiteSearchUseCase(site)){
                is NetworkState.Success<*> -> keywordSiteSearchList.value = _keywordSiteSearchList.data as List<String>
                is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",_keywordSiteSearchList.data.toString())
            }
        }
    }

    fun getSiteRecommendation(){
        viewModelScope.launch {
            when(val _keywordSiteRecommendationList = getSiteRecommendationUseCase()){
                is NetworkState.Success<*> -> keywordSiteRecommendationList.value = _keywordSiteRecommendationList.data as List<String>
                is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",_keywordSiteRecommendationList.data.toString())
            }
        }
    }

    fun getKeywordRecommendation(){
        viewModelScope.launch {
            when(val _keywordRecommendationList = getKeywordRecommendationUseCase()){
                is NetworkState.Success<*> -> keywordRecommendationList.value = _keywordRecommendationList.data as List<String>
                is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",_keywordRecommendationList.data.toString())
            }
        }
    }

    fun setRecentKeywordSearchList(keywordSearch:String){
        val setRecentKeywordSearchList = mutableListOf<String>()
        viewModelScope.launch {
            recentKeywordSearchList.value?.let { setRecentKeywordSearchList.addAll(it) }
            if(keywordSearch !in setRecentKeywordSearchList) setRecentKeywordSearchList.add(keywordSearch)
            setRecentSearchListUseCase("keyword_search_key",setRecentKeywordSearchList)
        }
    }

    fun getRecentKeywordSearchList(){
        viewModelScope.launch {
            getRecentSearchListUseCase("keyword_search_key").collectLatest { value ->
                recentKeywordSearchList.value = value
            }
        }
    }

    fun setRecentSiteSearchList(siteSearch:String){
        val setRecentSiteSearchList = mutableListOf<String>()
        viewModelScope.launch {
            recentSiteSearchList.value?.let { setRecentSiteSearchList.addAll(it) }
            if(siteSearch !in setRecentSiteSearchList) setRecentSiteSearchList.add(siteSearch)
            setRecentSearchListUseCase("site_search_key",setRecentSiteSearchList)
        }
    }

    fun getRecentSiteSearchList(){
        viewModelScope.launch {
            getRecentSearchListUseCase("site_search_key").collectLatest { value ->
                recentSiteSearchList.value = value
            }
        }
    }

    fun getAlarmSiteList(){
        Log.d("keywordaddscreen111","getalarmsitelist")
        val _alarmSiteList = mutableListOf<String>()
        alarmSiteData.getAllList { list: List<Site> ->
            list.onEach { site ->
                _alarmSiteList.add(site.site!!)
            }
        }
        alarmSiteList.value = _alarmSiteList
    }

    fun addAlarmSiteList(site:String){
        alarmSiteData.addSite(site)
        getAlarmSiteList()
    }

    fun deleteAlarmSite(site:String){
        alarmSiteData.deleteSite(site)
        getAlarmSiteList()
    }

    fun deleteAllSiteList(){
        alarmSiteData.deleteAllList()
        getAlarmSiteList()
    }

    fun pushKeyword(
        alarmCycle : Int,
        alarmMode : Boolean,
        isImportant: Boolean,
        name : String,
        siteList : List<String>,
        untilPressOkButton : Boolean,
        vibrationMode : Boolean
    ){
        val alarmCycleData = if(isImportant){
            when(alarmCycle){
                0 -> 5
                1 -> 10
                2-> 15
                3-> 30
                4-> 60
                5-> 120
                6-> 240
                7-> 360
                else -> 0
            }
        } else 0

        val silentModeData = if(alarmMode) 1 else 0
        val isImportantData = if(isImportant) 1 else 0
        val untilPressOkButtonData = if(untilPressOkButton && isImportant) 1 else 0
        val vibrationModeData = if(vibrationMode) 1 else 0

        val keywordResponse = KeywordAddResponse(
            alarmCycle = alarmCycleData,
            silentMode = silentModeData,
            isImportant = isImportantData,
            name = name,
            siteList = listOf("PORTAL"),
            untilPressOkButton = untilPressOkButtonData,
            vibrationMode = vibrationModeData
        )
        viewModelScope.launch {
            when(val pushKeywordResponse = pushKeywordUseCase(keywordResponse)){
                is NetworkState.Success<*> -> Log.d("KeywordAddViewModel",pushKeywordResponse.data.toString())
                is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",pushKeywordResponse.data.toString())
            }
        }
    }

}