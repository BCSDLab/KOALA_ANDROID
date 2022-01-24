package im.koala.bcsd.ui.login

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.R
import im.koala.bcsd.state.LoginState
import im.koala.bcsd.state.NetworkState
import im.koala.bcsd.ui.BaseViewModel
import im.koala.domain.constants.KAKAO
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

    private val _loginState = MutableLiveData<LoginState>(LoginState.NeedLogin)
    val loginState: LiveData<LoginState> get() = _loginState

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
        } else if (token != null) {
            getDeviceToken(KAKAO, token.accessToken)
        }
    }

    fun postGoogleAccessToken(
        clientId: String,
        clientSecret: String,
        authCode: String,
        onSuccess: (String) -> Unit,
        onFail: (Unit) -> Unit
    ) {
        viewModelScope.launch {
            googlePostAccessTokenUseCase(
                clientId = clientId,
                clientSecret = clientSecret,
                authCode = authCode,
                onSuccess = onSuccess,
                onFail = onFail
            )
        }
    }

    fun executeSnsLogin(snsType: String, accessToken: String, deviceToken: String) {
        _snsLoginState.value = NetworkState.Loading
        viewModelScope.launch {
            snsLoginUseCase(
                snsType = snsType,
                accessToken = accessToken,
                deviceToken = deviceToken,
                onSuccess = {
                    _snsLoginState.value = NetworkState.Success(it)
                },
                onFail = {
                    _snsLoginState.value = NetworkState.Fail(it)
                }
            )
        }
    }

    fun getDeviceToken(snsType: String, accessToken: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                loginFail(task.exception.toString())
                return@OnCompleteListener
            } else{
                executeSnsLogin(snsType, accessToken, task.result)
            }
        })
    }

    fun loginSuccess(){
        _loginState.value = LoginState.Success
    }
    fun loginFail(errorMessage: String?){
        _loginState.value = LoginState.Fail(errorMessage)
    }
    companion object {
        val TAG = this.javaClass.simpleName.toString()
    }
}