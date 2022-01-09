package im.koala.bcsd.datasource

import im.koala.bcsd.scenario.Scenario
import im.koala.data.api.GooglePostTokenApi
import im.koala.data.entity.GooglePostTokenRequestEntity
import im.koala.data.entity.TokenEntity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class TestGooglePostToken : GooglePostTokenApi {
    var scenario = Scenario.SUCCESS
    override suspend fun postGoogleToken(request: GooglePostTokenRequestEntity): Response<TokenEntity> {
        when(scenario){
            Scenario.SUCCESS->{
                return Response.success(TokenEntity().apply{
                    accessToken = "accessToken"
                    refreshToken = "refreshToken"
                })
            }
            Scenario.FAIL->{
                return Response.success(TokenEntity())
            }
            Scenario.ERROR->{
                return Response.error(
                    400,
                    "{\"code\":[\"400\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                )
            }
        }
    }
    fun changeScenario(scenario: Scenario) {
        this.scenario = scenario
    }
}