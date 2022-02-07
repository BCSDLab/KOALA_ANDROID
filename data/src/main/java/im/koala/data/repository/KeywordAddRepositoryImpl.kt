package im.koala.data.repository

import android.content.Context
import im.koala.data.repository.local.UserLocalDataSource
import im.koala.domain.state.NetworkState
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordAddResponse
import im.koala.domain.repository.KeywordAddRepository
import javax.inject.Inject

class KeywordAddRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
):KeywordAddRepository {

    override suspend fun getKeywordRecommendation(): NetworkState {
        val response = userRemoteDataSource.getKeywordRecommendation()
        var result: NetworkState = NetworkState.Uninitialized
        result = if(response.isSuccessful){
            NetworkState.Success(response.body()?.body)
        } else {
            val errorMsg = "code: ${response.code()}, msg: ${response.errorBody()?.string()}"
            NetworkState.Fail(errorMsg)
        }
        return result
    }

    override suspend fun getKeywordSiteRecommendation(): NetworkState {
        val response = userRemoteDataSource.getKeywordSiteRecommendation()
        var result: NetworkState = NetworkState.Uninitialized
        if(response.isSuccessful){
            result = NetworkState.Success(response.body()?.body)
        } else {
            CommonResponse.FAIL.apply { errorMessage = response.body()!!.code.toString() }
                .run { result = NetworkState.Fail(this) }
        }
        return result
    }

    override suspend fun getKeywordSiteSearch(site: String): NetworkState {
        val response = userRemoteDataSource.getKeywordSiteSearch(site)
        var result: NetworkState = NetworkState.Uninitialized
        if(response.isSuccessful){
            result = NetworkState.Success(response.body()?.body)
        } else {
            CommonResponse.FAIL.apply { errorMessage = response.body()!!.code.toString() }
                .run { result = NetworkState.Fail(this) }
        }
        return result
    }

    override suspend fun getKeywordSearch(keyword: String): NetworkState {
        val response = userRemoteDataSource.getKeywordSearch(keyword)
        var result: NetworkState = NetworkState.Uninitialized
        if(response.isSuccessful){
            result = NetworkState.Success(response.body()?.body)
        } else {
            CommonResponse.FAIL.apply { errorMessage = response.body()!!.code.toString() }
                .run { result = NetworkState.Fail(this) }
        }
        return result
    }

    override suspend fun pushKeyword(keywordResponse: KeywordAddResponse): NetworkState {
        val response = userRemoteDataSource.pushKeyword(keywordResponse)
        var result: NetworkState = NetworkState.Uninitialized
        result = if(response.isSuccessful){
            val msg = "msg: ${response.body()?.body} code: ${response.body()?.code}"
            NetworkState.Success(msg)
        } else{
            val msg = "msg: ${response.errorBody()?.string()}, code: ${response.body()?.code}"
            NetworkState.Fail(msg)
        }
        return result
    }

    override suspend fun getRecentSearchList(key: String): List<String> {
        return userLocalDataSource.getRecentSearchList(key)
    }

    override suspend fun setRecentSearchList(key: String, recentSearchList: List<String>) {
        userLocalDataSource.setRecentSearchList(key,recentSearchList)
    }
}