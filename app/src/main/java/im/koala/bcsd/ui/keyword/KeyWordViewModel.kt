package im.koala.bcsd.ui.keyword

import androidx.lifecycle.*
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.repository.KeywordAddRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class KeyWordViewModel(private val repository: KeywordAddRepository): ViewModel(){
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

    fun getAlarmSiteList(){
        alarmSiteList.value = repository.getAlarmSiteList()
    }

    fun setAlarmSiteList(site:String){
        repository.setAlarmSiteList(site)
    }

}

class KeyWordViewModelFactory(private val repository: KeywordAddRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return KeyWordViewModel(repository) as T
    }
}