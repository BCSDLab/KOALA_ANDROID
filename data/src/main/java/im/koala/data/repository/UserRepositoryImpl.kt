package im.koala.data.repository

import im.koala.data.repository.local.UserLocalDataSource
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.model.TokenResponse
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postSnsLogin(
        snsType: String,
        snsAccessToken: String,
        onSuccess: (TokenResponse) -> Unit,
        onFail: (CommonResponse) -> Unit
    ) {
        val response = userRemoteDataSource.postSnsLogin(snsType, snsAccessToken)
        if (response.isSuccessful) {
            if (response.body()?.code == 200) {
                TokenResponse().apply {
                    accessToken = response.body()?.body?.accessToken ?: run {
                        onFail(CommonResponse.UNKOWN); return
                    }
                    refreshToken = response.body()?.body?.refreshToken!!
                }.run {
                    userLocalDataSource.saveToken(this)
                    onSuccess(this)
                }
            } else {
                CommonResponse.FAIL.apply { errorMessage = response.body()!!.errorMessage }
                    .run { onFail(this) }
            }
        } else {
            onFail(CommonResponse.UNKOWN)
        }
    }

    override suspend fun getKeyword(
        onSuccess: (MutableList<KeywordResponse>) -> Unit,
        onFail: (CommonResponse) -> Unit
    ) {
        val response = userRemoteDataSource.getKeyword()
        if (response.isSuccessful) {
            if (response.body()?.code == 200) {
                val keywordList = mutableListOf<KeywordResponse>()
                for (it in response.body()?.body!!) {
                    keywordList.add(
                        KeywordResponse(
                            id = it.id,
                            name = it.name,
                            alarmCycle = it.alarmCycle,
                            noticeNum = it.noticeNum
                        )
                    )
                }
                onSuccess(keywordList)
            } else {
                CommonResponse.FAIL.apply { errorMessage = response.body()!!.errorMessage }
                    .run { onFail(this) }
            }
        } else {
            onFail(CommonResponse.UNKOWN)
        }
    }
}