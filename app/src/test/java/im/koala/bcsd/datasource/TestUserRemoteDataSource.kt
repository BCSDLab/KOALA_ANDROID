package im.koala.bcsd.datasource

import im.koala.bcsd.scenario.Scenario
import im.koala.data.entity.TokenBodyEntity
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.domain.entity.signup.SignUpResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class TestUserRemoteDataSource : UserRemoteDataSource {

    var scenario = Scenario.SUCCESS
    override suspend fun postSnsLogin(
        snsType: String,
        accessToken: String
    ): Response<TokenBodyEntity> {
        when (scenario) {
            Scenario.SUCCESS -> {
                return Response.success(TokenBodyEntity.SUCCESS)
            }
            Scenario.FAIL -> {
                return Response.success(TokenBodyEntity.FAIL)
            }
            Scenario.ERROR -> {
                return Response.error(
                    400,
                    "{\"code\":[\"400\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                )
            }
        }
    }

    override suspend fun checkIdIsAvailable(id: String): Boolean {
        return false
    }

    override suspend fun checkNicknameIsAvailable(nickname: String): Boolean {
        return false
    }

    override suspend fun checkEmailIsAvailable(email: String): Boolean {
        return false
    }

    override suspend fun signUp(
        accountId: String,
        accountEmail: String,
        accountNickname: String,
        password: String
    ): SignUpResult {
        return SignUpResult.Failed("")
    }

    fun chageScenario(scenario: Scenario) {
        this.scenario = scenario
    }
}