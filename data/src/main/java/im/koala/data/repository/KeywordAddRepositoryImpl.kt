package im.koala.data.repository

import im.koala.data.repository.local.KeywordAddLocalDataSource
import im.koala.data.repository.remote.KeywordAddRemoteDataSource
import im.koala.domain.state.Result
import im.koala.data.api.response.keywordadd.KeywordAddResponse
import im.koala.domain.repository.KeywordAddRepository
import javax.inject.Inject

class KeywordAddRepositoryImpl @Inject constructor(
    private val keywordAddRemoteDataSource: KeywordAddRemoteDataSource,
    private val keywordAddLocalDataSource: KeywordAddLocalDataSource
) : KeywordAddRepository {

    override suspend fun getKeywordRecommendation(): Result {
        val response = keywordAddRemoteDataSource.getKeywordRecommendation()

        return if (response.isSuccessful) {
            Result.Success(response.body()?.body)
        } else {
            val errorMsg = "code: ${response.code()}, msg: ${response.errorBody()?.string()}"
            Result.Fail(errorMsg)
        }
    }

    override suspend fun getKeywordSiteRecommendation(): Result {
        val response = keywordAddRemoteDataSource.getKeywordSiteRecommendation()

        return if (response.isSuccessful) {
            Result.Success(response.body()?.body)
        } else {
            val errorMsg = "code: ${response.code()}, msg: ${response.errorBody()?.string()}"
            Result.Fail(errorMsg)
        }
    }

    override suspend fun getKeywordSiteSearch(site: String): Result {
        val response = keywordAddRemoteDataSource.getKeywordSiteSearch(site)

        return if (response.isSuccessful) {
            Result.Success(response.body()?.body)
        } else {
            val errorMsg = "code: ${response.code()}, msg: ${response.errorBody()?.string()}"
            Result.Fail(errorMsg)
        }
    }

    override suspend fun getKeywordSearch(keyword: String): Result {
        val response = keywordAddRemoteDataSource.getKeywordSearch(keyword)

        return if (response.isSuccessful) {
            Result.Success(response.body()?.body)
        } else {
            val errorMsg = "code: ${response.code()}, msg: ${response.errorBody()?.string()}"
            Result.Fail(errorMsg)
        }
    }

    override suspend fun pushKeyword(
        alarmCycle: Int,
        alarmMode: Boolean,
        isImportant: Boolean,
        name: String,
        untilPressOkButton: Boolean,
        vibrationMode: Boolean,
        alarmSiteList: List<String>?
    ): Result {
        val alarmCycleData = if (isImportant) {
            when (alarmCycle) {
                0 -> 5
                1 -> 10
                2 -> 15
                3 -> 30
                4 -> 60
                5 -> 120
                6 -> 240
                7 -> 360
                else -> 0
            }
        } else 0
        val siteList = mutableListOf<String>()
        alarmSiteList?.onEach {
            when (it) {
                "아우누리" -> {
                    siteList.add("PORTAL")
                }
                "아우미르" -> {
                    siteList.add("DORM")
                }
                "한국기술교육대학교 유튜브" -> {
                    siteList.add("YOUTUBE")
                }
                "페이스북" -> {
                    siteList.add("FACEBOOK")
                }
                "인스타그램" -> {
                    siteList.add("INSTAGRAM")
                }
            }
        }

        val silentModeData = if (alarmMode) 1 else 0
        val isImportantData = if (isImportant) 1 else 0
        val untilPressOkButtonData = if (untilPressOkButton && isImportant) 1 else 0
        val vibrationModeData = if (vibrationMode) 1 else 0

        val keywordResponse = KeywordAddResponse(
            alarmCycle = alarmCycleData,
            silentMode = silentModeData,
            isImportant = isImportantData,
            name = name,
            siteList = siteList,
            untilPressOkButton = untilPressOkButtonData,
            vibrationMode = vibrationModeData
        )

        val response = keywordAddRemoteDataSource.pushKeyword(keywordResponse)

        return if (response.isSuccessful) {
            val msg = "msg: ${response.body()?.body} code: ${response.body()?.code}"
            Result.Success(msg)
        } else {
            val msg = "msg: ${response.errorBody()?.string()}, code: ${response.body()?.code}"
            Result.Fail(msg)
        }
    }

    override suspend fun deleteKeyword(keyword: String): Result {
        val response = keywordAddRemoteDataSource.deleteKeyword(keyword)

        return if (response.isSuccessful) {
            val msg = "msg: ${response.body()?.body} code: ${response.body()?.code}"
            Result.Success(msg)
        } else {
            val msg = "msg: ${response.errorBody()?.string()}, code: ${response.body()?.code}"
            Result.Fail(msg)
        }
    }

    override suspend fun getRecentSearchList(key: String): List<String> {
        return keywordAddLocalDataSource.getRecentSearchList(key)
    }

    override suspend fun setRecentSearchList(key: String, recentSearchList: List<String>) {
        keywordAddLocalDataSource.setRecentSearchList(key, recentSearchList)
    }
}