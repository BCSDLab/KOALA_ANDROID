package im.koala.bcsd.ui.login

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.data.OAuthLoginPreferenceManager
import com.nhn.android.naverlogin.data.OAuthLoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.R
import im.koala.bcsd.state.NetworkState
import im.koala.bcsd.ui.BaseViewModel
import im.koala.domain.constants.GOOGLE
import im.koala.domain.constants.KAKAO
import im.koala.domain.constants.NAVER
import im.koala.domain.usecase.GooglePostAccessTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val snsLoginUseCase: SnsLoginUseCase,
    private val googlePostAccessTokenUseCase: GooglePostAccessTokenUseCase
) : BaseViewModel() {
    private val _snsLoginState = MutableLiveData<NetworkState>(NetworkState.Uninitialized)
    val snsLoginState: LiveData<NetworkState> get() = _snsLoginState

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
        } else if (token != null) {
            executeSnsLogin(KAKAO, token.accessToken)
        }
    }

    fun kakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun naverLogin(context: Context) {
        val mOAuthLoginModule = OAuthLogin.getInstance()
        val mOAuthLoginHandler = object : OAuthLoginHandler() {
            override fun run(success: Boolean) {
                if (success) {
                    val accessToken = mOAuthLoginModule.getAccessToken(context)
                    executeSnsLogin(NAVER, accessToken)
                } else {
                    val errorCode = mOAuthLoginModule.getLastErrorCode(context).code
                    val errorDesc = mOAuthLoginModule.getLastErrorDesc(context)
                    Toast.makeText(
                        context,
                        "errorCode = $errorCode, errorDesc = $errorDesc",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        when (mOAuthLoginModule.getState(context)) {
            OAuthLoginState.NEED_INIT -> {
                mOAuthLoginModule.init(
                    context,
                    context.applicationContext.resources.getString(R.string.naver_client_id),
                    context.applicationContext.resources.getString(R.string.naver_client_secret),
                    context.getString(R.string.app_name)
                )
                mOAuthLoginModule.startOauthLoginActivity(context as Activity, mOAuthLoginHandler)
            }
            OAuthLoginState.NEED_LOGIN -> {
                mOAuthLoginModule.startOauthLoginActivity(context as Activity, mOAuthLoginHandler)
            }
            OAuthLoginState.NEED_REFRESH_TOKEN -> {
                val accessToken = mOAuthLoginModule.refreshAccessToken(context)
                if (accessToken != null) {
                    executeSnsLogin(NAVER, accessToken)
                } else {
                    mOAuthLoginModule.startOauthLoginActivity(
                        context as Activity,
                        mOAuthLoginHandler
                    )
                }
            }
            OAuthLoginState.OK -> {
                val mOAuthLoginPreferenceManager = OAuthLoginPreferenceManager(context)
                val accessToken = mOAuthLoginPreferenceManager.accessToken
                if (accessToken != null) {
                    executeSnsLogin(NAVER, accessToken)
                } else {
                    mOAuthLoginModule.startOauthLoginActivity(
                        context as Activity,
                        mOAuthLoginHandler
                    )
                }
            }
        }
    }

    fun getGoogleClient(context: Context): GoogleSignInClient {
        val googleSignInObtion = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.applicationContext.resources.getString(R.string.google_web_client_id))
            .requestServerAuthCode(context.applicationContext.resources.getString(R.string.google_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, googleSignInObtion)
    }

    fun postGoogleAccessToken(context: Context, authCode: String) {
        viewModelScope.launch {
            googlePostAccessTokenUseCase(
                clientId = context.applicationContext.resources.getString(R.string.google_web_client_id),
                clientSecret = context.applicationContext.resources.getString(R.string.google_web_client_secret),
                authCode = authCode,
                onSuccess = {
                    executeSnsLogin(GOOGLE, it)
                },
                onFail = {
                    Toast.makeText(context, R.string.google_login_fail, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    fun executeSnsLogin(snsType: String, token: String) {
        _snsLoginState.value = NetworkState.Loading
        viewModelScope.launch {
            snsLoginUseCase(
                snsType = snsType,
                accessToken = token,
                onSuccess = {
                    _snsLoginState.value = NetworkState.Success(it)
                },
                onFail = {
                    _snsLoginState.value = NetworkState.Fail(it)
                }
            )
        }
    }

    companion object {
        val TAG = this.javaClass.simpleName.toString()
    }
}