package im.koala.data.repository.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import im.koala.data.constants.ACCESS_TOKEN
import im.koala.data.constants.REFRESH_TOKEN
import im.koala.domain.model.TokenResponse
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserLocalDataSourceImpl@Inject constructor(
    context: Context
) : UserLocalDataSource {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "recent_search")
    private val gson: Gson = GsonBuilder().create()

    override fun saveToken(tokenResponse: TokenResponse) {
        Hawk.put(ACCESS_TOKEN, tokenResponse.accessToken)
        Hawk.put(REFRESH_TOKEN, tokenResponse.refreshToken)
    }

    override suspend fun getRecentSearchList(key: String): List<String> {
        val preferences = dataStore.data.first()
        val recentSearchKey = stringPreferencesKey(key)
        val recentKeywordSearchString: String = preferences[recentSearchKey] ?: "[]"
        return gson.fromJson(recentKeywordSearchString, Array<String>::class.java).asList()
    }

    override suspend fun setRecentSearchList(key: String, recentSearchList: List<String>) {
        val recentKeywordSearchListToString: String = gson.toJson(recentSearchList)
        val recentSearchKey = stringPreferencesKey(key)
        dataStore.edit { recent_search->
            recent_search[recentSearchKey] = recentKeywordSearchListToString
        }
    }

}