package im.koala.bcsd.ui.keyword

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import im.koala.domain.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.data.repository.local.AlarmSiteDataSource
import im.koala.data.repository.local.Site
import im.koala.domain.model.KeywordAddResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.usecase.GetKeywordListUseCase
import im.koala.domain.usecase.keyword.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    private val getKeywordListUseCase: GetKeywordListUseCase,
    private val alarmSiteData:AlarmSiteDataSource
): ViewModel(){
    val keywordSearchList: MutableLiveData<List<String>> = MutableLiveData()
    val keywordRecommendationList: MutableLiveData<List<String>> = MutableLiveData()
    val recentKeywordSearchList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))

    val siteSearchList: MutableLiveData<List<String>> = MutableLiveData()
    val siteRecommendationList: MutableLiveData<List<String>> = MutableLiveData()
    val recentSiteSearchList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))
    val alarmSiteList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))

    val keywordNameList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))

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
                is NetworkState.Success<*> -> siteSearchList.value = _keywordSiteSearchList.data as List<String>
                is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",_keywordSiteSearchList.data.toString())
            }
        }
    }

    fun getSiteRecommendation(){
        viewModelScope.launch {
            when(val _keywordSiteRecommendationList = getSiteRecommendationUseCase()){
                is NetworkState.Success<*> -> siteRecommendationList.value = _keywordSiteRecommendationList.data as List<String>
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
        untilPressOkButton : Boolean,
        vibrationMode : Boolean
    ){
        viewModelScope.launch {
            when(val pushKeywordResponse = pushKeywordUseCase(
                alarmCycle,
                alarmMode,
                isImportant,
                name,
                untilPressOkButton,
                vibrationMode,
                alarmSiteList.value
            )){
                is NetworkState.Success<*> -> Log.d("KeywordAddViewModel",pushKeywordResponse.data.toString())
                is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",pushKeywordResponse.data.toString())
            }
        }
    }

    fun getKeywordNameList(){
        viewModelScope.launch {
            getKeywordListUseCase().collect{
                when(it){
                    is NetworkState.Success<*> -> {
                        val keywordList = it.data as List<KeywordResponse>
                        val _keywordNameList:MutableList<String> = mutableListOf()
                        keywordList.forEach{ _keywordNameList.add(it.name) }
                        keywordNameList.value = _keywordNameList
                    }
                    is NetworkState.Fail<*> -> Log.d("KeywordAddViewModel",it.data.toString())
                }
            }
        }
    }

}