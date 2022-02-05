package im.koala.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.module.ApiModule
import im.koala.domain.model.KeywordAddResponse
import kotlinx.coroutines.flow.first
import retrofit2.Response

class KeywordAddRepository111(context: Context) {

    suspend fun pushKeyword(keywordAddResponse: KeywordAddResponse): Response<ResponseWrapper<String>> {
        return ApiModule().provideAuthApi().pushKeyword(keywordAddResponse)
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
        private val gson:Gson = GsonBuilder().create()
        private var alarmSiteListToString = "[]"
        private val _alarmSiteList = mutableListOf<String>()
    }
}