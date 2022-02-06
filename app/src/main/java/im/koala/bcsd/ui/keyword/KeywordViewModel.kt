package im.koala.bcsd.ui.keyword

import androidx.compose.runtime.State
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import im.koala.domain.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.repository.KeywordAddRepository111
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordAddResponse
import im.koala.domain.usecase.keyword.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    private val repository111: KeywordAddRepository111,
    private val getKeywordRecommendationUseCase: GetKeywordRecommendationUseCase,
    private val getKeywordSearchUseCase: GetKeywordSearchUseCase,
    private val getSiteRecommendationUseCase: GetSiteRecommendationUseCase,
    private val getSiteSearchUseCase: GetSiteSearchUseCase,
    private val getRecentSearchListUseCase: GetRecentSearchListUseCase,
    private val setRecentSearchListUseCase: SetRecentSearchListUseCase
): ViewModel(){
    val pushKeywordResponse: MutableLiveData<Response<ResponseWrapper<String>>> = MutableLiveData()

    private val _keywordRecommendationList: MutableState<ListUi> = mutableStateOf(ListUi())
    val keywordRecommendationList: State<ListUi> get() = _keywordRecommendationList

    private val _keywordSearchList: MutableState<ListUi> = mutableStateOf(ListUi())
    val keywordSearchList: State<ListUi> get() = _keywordSearchList

    private val _siteSearchList: MutableState<ListUi> = mutableStateOf(ListUi())
    val siteSearchList: State<ListUi> get() = _siteSearchList

    private val _siteRecommendationList: MutableState<ListUi> = mutableStateOf(ListUi())
    val siteRecommendationList: State<ListUi> get() = _siteRecommendationList

    private val _recentKeywordSearchList: MutableState<ListUi> = mutableStateOf(ListUi())
    val recentKeywordSearchList:State<ListUi> get() = _recentKeywordSearchList

    private val _recentSiteSearchList: MutableState<ListUi> = mutableStateOf(ListUi())
    val recentSiteSearchList:State<ListUi> get() = _recentSiteSearchList

    val alarmSiteList:MutableLiveData<List<String>> = MutableLiveData(listOf(""))

    val keywordState: StateFlow<NetworkState>
    val domainKeywordSharedFlow = MutableSharedFlow<NetworkState>()

    init {

        val onGetKeywordListUseCase = domainKeywordSharedFlow.shareIn(
            viewModelScope,
            SharingStarted.Eagerly,
            0
        )
        keywordState = onGetKeywordListUseCase.stateIn(viewModelScope, SharingStarted.Eagerly, NetworkState.Uninitialized)

        viewModelScope.launch(Dispatchers.IO) {
            keywordState.collectLatest {
                when (it) {
                    is NetworkState.Success<*> -> {
                        _keywordRecommendationList.value = _keywordRecommendationList.value.copy(list = (it.data as MutableList<String>))
                        _keywordSearchList.value = _keywordSearchList.value.copy(list = it.data as MutableList<String>)
                        _siteSearchList.value = _siteSearchList.value.copy(list = it.data as MutableList<String>)
                        _siteRecommendationList.value = _siteRecommendationList.value.copy(list = it.data as MutableList<String>)
                    }
                    is NetworkState.Fail<*> -> {
                        val response = it.data as CommonResponse
                        _keywordRecommendationList.value = _keywordRecommendationList.value.copy(errorMessage = response.errorMessage!!)
                        _keywordSearchList.value = _keywordSearchList.value.copy(errorMessage = response.errorMessage!!)
                        _siteSearchList.value = _siteSearchList.value.copy(errorMessage = response.errorMessage!!)
                        _siteRecommendationList.value = _siteRecommendationList.value.copy(errorMessage = response.errorMessage!!)
                    }
                }
            }
        }
    }

    fun getKeywordSearchList(keyword:String){
        getKeywordSearchUseCase(keyword).onEach {
            domainKeywordSharedFlow.emit(it)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun getKeywordSiteSearch(site:String){
        getSiteSearchUseCase(site).onEach {
            domainKeywordSharedFlow.emit(it)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun getSiteRecommendation(){
        getSiteRecommendationUseCase().onEach {
            domainKeywordSharedFlow.emit(it)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun getKeywordRecommendation(){
        getKeywordRecommendationUseCase().onEach {
            domainKeywordSharedFlow.emit(it)
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
    // keyword : keyword_search_key
    // site : site_search_key
    fun setRecentKeywordSearchList(keywordSearch:String){
        viewModelScope.launch{
//            setRecentSearchListUseCase("keyword_search_key")
        }
    }

    fun getRecentKeywordSearchList(){
        getRecentSearchListUseCase("keyword_search_key")
    }

    fun setRecentSiteSearchList(siteSearch:String){

    }

    fun getRecentSiteSearchList(){
        getRecentSearchListUseCase("site_search_key")
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
            pushKeywordResponse.value = repository111.pushKeyword(keywordResponse)
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
        alarmSiteList.value = repository111.getAlarmSiteList()
    }

    fun setAlarmSiteList(site:String){
        repository111.setAlarmSiteList(site)
    }

    fun deleteSiteList(site:String){
        repository111.deleteSiteList(site)
    }

    fun resetSiteList(){
        repository111.resetSiteList()
    }

}

data class ListUi(
    val list: MutableList<String> = mutableListOf(),
    var errorMessage: String = ""
)