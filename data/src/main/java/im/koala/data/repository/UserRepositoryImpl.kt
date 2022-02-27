package im.koala.data.repository

import android.util.Log
import im.koala.data.mapper.user.toTokenResponse
import im.koala.data.repository.local.UserLocalDataSource
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.model.TokenResponse
import im.koala.domain.repository.UserRepository
import im.koala.domain.state.Result
import im.koala.domain.util.toSHA256
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postSnsLogin(
        snsType: String,
        snsAccessToken: String,
        deviceToken: String
    ): Result {
        val response = userRemoteDataSource.postSnsLogin(snsType, snsAccessToken, deviceToken)
        var result: Result = Result.Uninitialized
        if (response.isSuccessful) {
            TokenResponse(
                accessToken = response.body()?.body?.accessToken ?: run {
                    return Result.Fail(CommonResponse.UNKOWN)
                },
                refreshToken = response.body()?.body?.refreshToken!!
            ).run {
                userLocalDataSource.saveToken(this)
                result = Result.Success(this)
            }
        } else {
            CommonResponse.FAIL.apply { errorMessage = response.body()!!.errorMessage }
                .run { result = Result.Fail(this) }
        }
        return result
    }

    override suspend fun login(
        deviceToken: String,
        id: String,
        password: String
    ): kotlin.Result<TokenResponse> {
        return try {
            val tokenEntity = userRemoteDataSource.login(
                accountId = id,
                password = password,
                deviceToken = deviceToken
            )

            Log.d("Access token", tokenEntity.accessToken)
            userLocalDataSource.saveToken(tokenEntity.toTokenResponse())

            kotlin.Result.success(tokenEntity.toTokenResponse())
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun loginWithoutSignUp(deviceToken: String): kotlin.Result<TokenResponse> {
        return try {
            val tokenEntity = userRemoteDataSource.loginWithoutSignUp(
                deviceToken = deviceToken
            )

            userLocalDataSource.saveToken(tokenEntity.toTokenResponse())

            kotlin.Result.success(tokenEntity.toTokenResponse())
        } catch (e: Exception) {
            kotlin.Result.failure(e)
        }
    }

    override suspend fun getKeyword(): Result {
        val response = userRemoteDataSource.getKeyword()
        var result: Result = Result.Uninitialized
        if (response.isSuccessful) {
            val keywordList = mutableListOf<KeywordResponse>()
            response.body()?.body?.let {
                it.map {
                    KeywordResponse(
                        id = it.id,
                        name = it.name,
                        alarmCycle = it.alarmCycle,
                        noticeNum = it.noticeNum
                    )
                }.forEach {
                    keywordList.add(it)
                }
            }
            result = Result.Success(keywordList)
        } else {
            CommonResponse.FAIL.apply { errorMessage = response.body()!!.errorMessage }
                .run { result = Result.Fail(this) }
        }
        return result
    }

    override suspend fun checkIdDuplicate(id: String): Boolean {
        return !userRemoteDataSource.checkIdIsAvailable(id)
    }

    override suspend fun checkEmailDuplicate(email: String): Boolean {
        return !userRemoteDataSource.checkEmailIsAvailable(email)
    }

    override suspend fun checkNicknameDuplicate(nickname: String): Boolean {
        return !userRemoteDataSource.checkNicknameIsAvailable(nickname)
    }

    override suspend fun signUp(
        accountId: String,
        password: String,
        accountEmail: String,
        accountNickname: String
    ): SignUpResult {
        return userRemoteDataSource.signUp(
            accountId,
            accountEmail,
            accountNickname,
            password.toSHA256()
        )
    }
}