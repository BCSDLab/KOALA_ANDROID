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

class RecentKeywordSearchDataSource @Inject constructor(
    context: Context
) : RecentSearchDataSource {
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "recent_search")

    override suspend fun getRecentSearchList(): List<String> {
        val preferences = dataStore.data.first()
        val recentKeywordSearchString: String = preferences[recentKeywordSearchKey] ?: "[]"
        return gson.fromJson(recentKeywordSearchString, Array<String>::class.java).asList()
    }

    override suspend fun setRecentSearchList(recentSearchList: List<String>) {
        val recentKeywordSearchListToString: String = gson.toJson(recentSearchList)
        dataStore.edit { recent_search->
            recent_search[recentKeywordSearchKey] = recentKeywordSearchListToString
        }
    }

    companion object{
        val gson: Gson = GsonBuilder().create()
        private val recentKeywordSearchKey = stringPreferencesKey("site_keyword_key")
    }
}