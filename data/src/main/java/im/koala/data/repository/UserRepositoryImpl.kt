package im.koala.data.repository

import im.koala.data.repository.local.UserLocalDataSource
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.TokenResponse
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor (
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : UserRepository {
    override suspend fun postSnsLogin(
        snsType: String,
        accessToken: String,
        onSuccess: (TokenResponse) -> Unit,
        onFail: (CommonResponse) -> Unit
    ) {
        val response = userRemoteDataSource.postSnsLogin(snsType, accessToken)
        if (response.isSuccessful) {
            if (response.body()?.code == 200) {
                TokenResponse().apply {
                    this.accessToken = response.body()?.body?.accessToken ?: run {
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
        return userRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
    }
}