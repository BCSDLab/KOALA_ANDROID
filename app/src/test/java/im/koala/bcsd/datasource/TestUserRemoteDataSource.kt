package im.koala.bcsd.datasource

import im.koala.bcsd.scenario.Scenario
import im.koala.data.entity.TokenBodyEntity
import im.koala.data.repository.remote.UserRemoteDataSource
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Response
import okhttp3.ResponseBody.Companion.toResponseBody
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
    fun chageScenario(scenario: Scenario) {
        this.scenario = scenario
    }
}