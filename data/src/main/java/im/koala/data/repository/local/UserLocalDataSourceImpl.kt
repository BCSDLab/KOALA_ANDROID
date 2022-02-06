package im.koala.data.repository.local

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import im.koala.data.constants.ACCESS_TOKEN
import im.koala.data.constants.REFRESH_TOKEN
import im.koala.domain.model.TokenResponse
import javax.inject.Inject

class UserLocalDataSourceImpl@Inject constructor() : UserLocalDataSource {
    private val gson: Gson = GsonBuilder().create()

    override fun saveToken(tokenResponse: TokenResponse) {
        Hawk.put(ACCESS_TOKEN, tokenResponse.accessToken)
        Hawk.put(REFRESH_TOKEN, tokenResponse.refreshToken)
    }

    override suspend fun getRecentSearchList(key: String): List<String> {
        return Hawk.get(key,mutableListOf())
    }

    override suspend fun setRecentSearchList(key: String, recentSearchList: List<String>) {
        Hawk.put(key,recentSearchList)
    }

}