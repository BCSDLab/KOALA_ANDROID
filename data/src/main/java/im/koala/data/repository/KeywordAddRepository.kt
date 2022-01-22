package im.koala.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import im.koala.data.api.response.KeywordResponse
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.module.ApiModule
import kotlinx.coroutines.flow.first
import retrofit2.Response

class KeywordAddRepository(context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "recent_search")

    suspend fun getKeywordSearch(keyword:String): ResponseWrapper<List<String>> {
        return ApiModule().provideAuthApi().getKeywordSearch(keyword)
    }

    suspend fun getKeywordSiteSearch(site:String): ResponseWrapper<List<String>> {
        return ApiModule().provideAuthApi().getKeywordSiteSearch(site)
    }

    suspend fun getKeywordSiteRecommendation(): ResponseWrapper<List<String>> {
        return ApiModule().provideAuthApi().getKeywordSiteRecommendation()
    }

    suspend fun getKeywordRecommendation(): ResponseWrapper<List<String>> {
        return ApiModule().provideAuthApi().getKeywordRecommendation()
    }

    suspend fun setRecentKeywordSearchList(recentKeywordSearchList:List<String>){
        val recentKeywordSearchListToString: String = gson.toJson(recentKeywordSearchList)
        dataStore.edit { recent_search->
            recent_search[recentKeywordSearchKey] = recentKeywordSearchListToString
        }
    }

    suspend fun getRecentKeywordSearchList():List<String>{
        val preferences = dataStore.data.first()
        val recentKeywordSearchString: String = preferences[recentKeywordSearchKey] ?: "[]"
        return gson.fromJson(recentKeywordSearchString, Array<String>::class.java).asList()
    }

    suspend fun setRecentSiteSearchList(recentSiteSearchList:List<String>){
        val recentSiteSearchListToString: String = gson.toJson(recentSiteSearchList)
        dataStore.edit { recent_search->
            recent_search[recentSiteSearchKey] = recentSiteSearchListToString
        }
    }

    suspend fun getRecentSiteSearchList():List<String>{
        val preferences = dataStore.data.first()
        val recentSiteSearchString: String = preferences[recentSiteSearchKey] ?: "[]"
        return gson.fromJson(recentSiteSearchString, Array<String>::class.java).asList()
    }

    suspend fun pushKeyword(keywordResponse: KeywordResponse): Response<ResponseWrapper<String>> {
        return ApiModule().provideAuthApi().pushKeyword(keywordResponse)
    }

    fun getAlarmSiteList():List<String>{
        return gson.fromJson(alarmSiteListToString,Array<String>::class.java).asList()
    }

    fun setAlarmSiteList(site:String){
        val alarmSiteStringToList:List<String> = getAlarmSiteList()
        _alarmSiteList.addAll(alarmSiteStringToList)
        if(site !in _alarmSiteList) _alarmSiteList.add(site)
        alarmSiteListToString = gson.toJson(_alarmSiteList)
        _alarmSiteList.clear()
    }

    fun deleteSiteList(site:String){
        val alarmSiteStringToList:List<String> = getAlarmSiteList()
        _alarmSiteList.addAll(alarmSiteStringToList)
        _alarmSiteList.remove(site)
        alarmSiteListToString = gson.toJson(_alarmSiteList)
        _alarmSiteList.clear()
    }

    fun resetSiteList(){
        alarmSiteListToString = gson.toJson(_alarmSiteList)
    }

    companion object{
        val gson:Gson = GsonBuilder().create()
        val recentSiteSearchKey = stringPreferencesKey("site_search_key")
        val recentKeywordSearchKey = stringPreferencesKey("site_keyword_key")
        var alarmSiteListToString = "[]"
        val _alarmSiteList = mutableListOf<String>()
    }
}