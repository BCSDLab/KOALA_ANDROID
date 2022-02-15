package im.koala.data.repository.local

import com.orhanobut.hawk.Hawk
import javax.inject.Inject

class KeywordAddLocalDataSourceImpl @Inject constructor() : KeywordAddLocalDataSource {

    override suspend fun getRecentSearchList(key: String): List<String> {
        return Hawk.get(key, mutableListOf())
    }

    override suspend fun setRecentSearchList(key: String, recentSearchList: List<String>) {
        Hawk.put(key, recentSearchList)
    }
}