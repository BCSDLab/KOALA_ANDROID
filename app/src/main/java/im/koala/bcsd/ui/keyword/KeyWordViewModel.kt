package im.koala.bcsd.ui.keyword

import android.util.Log
import androidx.lifecycle.*
import im.koala.data.api.response.KeywordResponse
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.repository.KeywordAddRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class KeyWordViewModel(private val repository: KeywordAddRepository): ViewModel(){
    val pushKeywordResponse:MutableLiveData<Response<ResponseWrapper<String>>> = MutableLiveData()
    val keywordSearchList: MutableLiveData<ResponseWrapper<List<String>>> = MutableLiveData()
    val keywordSiteSearchList: MutableLiveData<ResponseWrapper<List<String>>> = MutableLiveData()
    val keywordSiteRecommendationList: MutableLiveData<ResponseWrapper<List<String>>> = MutableLiveData()
    val keywordRecommendationList: MutableLiveData<ResponseWrapper<List<String>>> = MutableLiveData()
    val recentKeywordSearchList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))
    val recentSiteSearchList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))
    val alarmSiteList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))

    fun getKeywordSearchList(keyword:String){
        viewModelScope.launch {
            val response = repository.getKeywordSearch(keyword)
            keywordSearchList.value = response
        }
    }

    fun getKeywordSiteSearch(site:String){
        viewModelScope.launch {
            val response = repository.getKeywordSiteSearch(site)
            keywordSiteSearchList.value = response
        }
    }

    fun getKeywordSiteRecommendation(){
        viewModelScope.launch {
            val response = repository.getKeywordSiteRecommendation()
            keywordSiteRecommendationList.value = response
        }
    }

    fun getKeywordRecommendation(){
        viewModelScope.launch {
            val response = repository.getKeywordRecommendation()
            keywordRecommendationList.value = response
        }
    }

    fun setRecentKeywordSearchList(keywordSearch:String){
        val setRecentKeywordSearchList = mutableListOf<String>()
        viewModelScope.launch {
            setRecentKeywordSearchList.addAll(recentKeywordSearchList.value!!)
            if(keywordSearch !in setRecentKeywordSearchList) setRecentKeywordSearchList.add(keywordSearch)
            repository.setRecentKeywordSearchList(setRecentKeywordSearchList)
        }
    }

    fun getRecentKeywordSearchList(){
        viewModelScope.launch {
            recentKeywordSearchList.value = repository.getRecentKeywordSearchList()
        }
    }

    fun setRecentSiteSearchList(siteSearch:String){
        val setRecentSiteSearchList = mutableListOf<String>()
        viewModelScope.launch {
            setRecentSiteSearchList.addAll(recentSiteSearchList.value!!)
            if(siteSearch !in setRecentSiteSearchList) setRecentSiteSearchList.add(siteSearch)
            repository.setRecentSiteSearchList(setRecentSiteSearchList)
        }
    }

    fun getRecentSiteSearchList(){
        viewModelScope.launch {
            recentSiteSearchList.value = repository.getRecentSiteSearchList()
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

        val keywordResponse = KeywordResponse(
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
            pushKeywordResponse.value = repository.pushKeyword(keywordResponse)
            Log.d("test111","isSuccessful : ${pushKeywordResponse.value?.isSuccessful.toString()}")
            Log.d("test111","body.body : ${pushKeywordResponse.value?.body()?.body.toString()}")
            Log.d("test111","body.code : ${pushKeywordResponse.value?.body()?.code.toString()}")
            Log.d("test111","code : ${pushKeywordResponse.value?.code().toString()}")
            Log.d("test111","message : ${pushKeywordResponse.value?.message().toString()}")
            Log.d("test111","errorBody : ${pushKeywordResponse.value?.errorBody()?.string()!!}")
            Log.d("test111","headers : ${pushKeywordResponse.value?.headers().toString()}")
        }
    }

    fun getAlarmSiteList(){
        alarmSiteList.value = repository.getAlarmSiteList()
    }

    fun setAlarmSiteList(site:String){
        repository.setAlarmSiteList(site)
    }

    fun deleteSiteList(site:String){
        repository.deleteSiteList(site)
    }

    fun resetSiteList(){
        repository.resetSiteList()
    }

}

class KeyWordViewModelFactory(private val repository: KeywordAddRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KeyWordViewModel(repository) as T
    }
}