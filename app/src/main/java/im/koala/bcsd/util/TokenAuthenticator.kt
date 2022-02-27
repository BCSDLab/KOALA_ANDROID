package im.koala.bcsd.util

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.os.HandlerCompat
import com.orhanobut.hawk.Hawk
import im.koala.bcsd.R
import im.koala.bcsd.ui.login.LoginActivity
import im.koala.data.api.AuthApi
import im.koala.data.constants.ACCESS_TOKEN
import im.koala.data.constants.REFRESH_TOKEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val context: Context,
    private val refreshRetrofit: Retrofit
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {
        var request: Request? = null
        try {
            if (response.code != 401) request = null
            else {
                val tokenResult = CoroutineScope(Dispatchers.Main).async {
                    getRefreshedJwtToken().collectLatest { tokenResult ->
                        tokenResult.onFailure {
                            goToLoginActivity()
                            request = null
                        }

                        tokenResult.onSuccess { accessToken ->
                            if (accessToken.isEmpty()) {
                                goToLoginActivity()
                                request = null
                            }
                            request = getRequest(response, accessToken)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            goToLoginActivity()
            request = null
        }

        request
    }

    private suspend fun getRefreshedJwtToken(): Flow<Result<String>> {
        val authApi = refreshRetrofit.create(AuthApi::class.java)
        return flow {
            val tokenResponse = authApi.refresh()
            if (!tokenResponse.isSuccessful) {
                val errorMessage = kotlin.runCatching { tokenResponse.errorBody()?.string() ?: "" }
                throw RuntimeException(errorMessage.getOrDefault(""))
            } else if (tokenResponse.body() == null) {
                throw RuntimeException("Token Empty!")
            } else {
                emit(tokenResponse.body())
            }
        }
            .retry(5) { true }
            .map { token ->
                if (token == null) {
                    Result.failure(RuntimeException("Token Empty"))
                } else if (token.accessToken == null || token.refreshToken == null) {
                    Result.failure(RuntimeException("Token Empty"))
                } else {
                    Log.d("TokenAutenticator", "new access token : ${token.accessToken}")
                    Log.d("TokenAutenticator", "new refresh token : ${token.refreshToken}")
                    Hawk.put(ACCESS_TOKEN, token.accessToken)
                    Hawk.put(REFRESH_TOKEN, token.refreshToken)
                    Result.success(token.accessToken)
                }
            }
    }

    private fun getRequest(response: Response, token: String): Request {
        return response.request
            .newBuilder()
            .removeHeader("Authorization")
            .addHeader("Authorization", "Bearer $token")
            .build()
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    private fun goToLoginActivity() {
        val handler = HandlerCompat.createAsync(Looper.getMainLooper())
        Intent(context.applicationContext, LoginActivity::class.java).run {
            handler.post {
                Toast.makeText(
                    context.applicationContext,
                    context.getString(R.string.token_expired),
                    Toast.LENGTH_SHORT
                ).show()
            }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(this)
        }
    }

    companion object {
        const val TAG = "TokenAuthenticator"
    }
}