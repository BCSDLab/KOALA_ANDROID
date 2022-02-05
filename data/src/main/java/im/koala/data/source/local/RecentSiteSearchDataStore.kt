package im.koala.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import im.koala.data.source.RecentSearchDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RecentSiteSearchDataStore @Inject constructor(
    context: Context
) : RecentSearchDataSource {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "recent_search")

    override suspend fun getRecentSearchList(): List<String> {
        val preferences = dataStore.data.first()
        val recentSiteSearchString: String = preferences[recentSiteSearchKey] ?: "[]"
        return gson.fromJson(recentSiteSearchString, Array<String>::class.java).asList()
    }

    override suspend fun setRecentSearchList(recentSearchList: List<String>) {
        val recentSiteSearchListToString: String = gson.toJson(recentSearchList)
        dataStore.edit { recent_search->
            recent_search[recentSiteSearchKey] = recentSiteSearchListToString
        }
    }

    companion object{
        private val recentSiteSearchKey = stringPreferencesKey("site_search_key")
        private val gson: Gson = GsonBuilder().create()
    }
}