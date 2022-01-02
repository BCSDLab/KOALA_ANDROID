package im.koala.bcsd.ui.login

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
import im.koala.domain.usecase.KakaoLoginUseCase
import im.koala.domain.usecase.NaverLoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel@Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val naverLoginUseCase: NaverLoginUseCase
) : BaseViewModel() {
    private val _snsLoginState = MutableLiveData<NetworkState>(NetworkState.Uninitialized)
    val snsLoginState: LiveData<NetworkState> get() = _snsLoginState

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
        } else if (token != null) {
            executeKakaoLogin(token.accessToken)
        }
    }
    fun kakaoLogin(context: Context) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
    fun executeKakaoLogin(token: String) {
        _snsLoginState.value = NetworkState.Loading
        viewModelScope.launch {
            kakaoLoginUseCase(
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
    fun naverLogin(context : Context){
        val mOAuthLoginModule = OAuthLogin.getInstance()
        val mOAuthLoginHandler = object : OAuthLoginHandler(){
            override fun run(success: Boolean) {
                if(success){
                    val accessToken = mOAuthLoginModule.getAccessToken(context)
                    executeNaverLogin(accessToken)
                } else{
                    val errorCode = mOAuthLoginModule.getLastErrorCode(context).code
                    val errorDesc = mOAuthLoginModule.getLastErrorDesc(context)
                    Toast.makeText(context, "errorCode = $errorCode, errorDesc = $errorDesc", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Log.e("loginState", mOAuthLoginModule.getState(context).toString())
        when(mOAuthLoginModule.getState(context)){
            OAuthLoginState.NEED_INIT -> {
                mOAuthLoginModule.init(
                    context,
                    context.applicationContext.resources.getString(R.string.naver_client_id),
                    context.applicationContext.resources.getString(R.string.naver_clinet_secret),
                    context.getString(R.string.app_name)
                )
                mOAuthLoginModule.startOauthLoginActivity(context as Activity,mOAuthLoginHandler)
            }
            OAuthLoginState.NEED_LOGIN -> {
                mOAuthLoginModule.startOauthLoginActivity(context as Activity,mOAuthLoginHandler)
            }
            OAuthLoginState.NEED_REFRESH_TOKEN ->{
                val accessToken = mOAuthLoginModule.refreshAccessToken(context)
                if(accessToken != null){
                    executeNaverLogin(accessToken)
                } else {
                    mOAuthLoginModule.startOauthLoginActivity(context as Activity,mOAuthLoginHandler)
                }
            }
            OAuthLoginState.OK -> {
                val mOAuthLoginPreferenceManager = OAuthLoginPreferenceManager(context)
                val accessToken = mOAuthLoginPreferenceManager.accessToken
                if(accessToken != null){
                    executeNaverLogin(accessToken)
                } else {
                    mOAuthLoginModule.startOauthLoginActivity(context as Activity,mOAuthLoginHandler)
                }
            }
        }
    }
    fun executeNaverLogin(token : String){
        Log.e("loginTest","LoginTest")
        _snsLoginState.value = NetworkState.Loading
        viewModelScope.launch {
            naverLoginUseCase(
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