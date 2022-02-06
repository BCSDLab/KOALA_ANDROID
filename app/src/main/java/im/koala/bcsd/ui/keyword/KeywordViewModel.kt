package im.koala.bcsd.ui.keyword

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import im.koala.domain.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.data.api.response.ResponseWrapper
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
    private val setRecentSearchListUseCase: SetRecentSearchListUseCase
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
            getKeywordSearchUseCase(keyword).collectLatest {
                when(it){
                    is NetworkState.Success<*> -> keywordSearchList.value = it.data as List<String>
                    is NetworkState.Fail<*> -> {
                        Log.d("ttt3333",it.data.toString())
                        keywordSearchList.value = mutableListOf()
                    }
                    else -> keywordSearchList.value = mutableListOf()
                }
            }
        }
    }

    fun getKeywordSiteSearch(site:String){
        viewModelScope.launch {
            getSiteSearchUseCase(site).collectLatest {
                when(it){
                    is NetworkState.Success<*> -> keywordSiteSearchList.value = it.data as List<String>
                    is NetworkState.Fail<*> -> {
                        Log.d("ttt3333",it.data.toString())
                        keywordSiteSearchList.value = mutableListOf()
                    }
                    else -> keywordSiteSearchList.value = mutableListOf()
                }
            }
        }
    }

    fun getSiteRecommendation(){
        viewModelScope.launch {
            getSiteRecommendationUseCase().collectLatest {
                when(it){
                    is NetworkState.Success<*> -> keywordSiteRecommendationList.value = it.data as List<String>
                    is NetworkState.Fail<*> -> {
                        Log.d("ttt3333",it.data.toString())
                        keywordSiteRecommendationList.value = mutableListOf()
                    }
                    else -> keywordSiteRecommendationList.value = mutableListOf()
                }
            }
        }
    }

    fun getKeywordRecommendation(){
        viewModelScope.launch {
            getKeywordRecommendationUseCase().collectLatest {
                when(it){
                    is NetworkState.Success<*> -> keywordRecommendationList.value = it.data as List<String>
                    is NetworkState.Fail<*> -> {
                        Log.d("ttt3333",it.data.toString())
                        keywordRecommendationList.value = mutableListOf()
                    }
                    else -> keywordRecommendationList.value = mutableListOf()
                }
            }
        }
    }

    fun setRecentKeywordSearchList(keywordSearch:String){
        val setRecentKeywordSearchList = mutableListOf<String>()
        viewModelScope.launch {
            setRecentKeywordSearchList.addAll(recentKeywordSearchList.value!!)
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
            setRecentSiteSearchList.addAll(recentSiteSearchList.value!!)
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
        Log.d("test111",keywordResponse.toString())
        viewModelScope.launch {
            Log.d("test111","isSuccessful : ${pushKeywordResponse.value?.isSuccessful.toString()}")
            Log.d("test111","body.body : ${pushKeywordResponse.value?.body()?.body.toString()}")
            Log.d("test111","body.code : ${pushKeywordResponse.value?.body()?.code.toString()}")
            Log.d("test111","code : ${pushKeywordResponse.value?.code().toString()}")
            Log.d("test111","message : ${pushKeywordResponse.value?.message().toString()}")
            Log.d("test111","errorBody : ${pushKeywordResponse.value?.errorBody()?.string()!!}")
            Log.d("test111","headers : ${pushKeywordResponse.value?.headers().toString()}")
        }
    }

}