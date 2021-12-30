package im.koala.bcsd.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.state.NetworkState
import im.koala.bcsd.ui.BaseViewModel
import im.koala.domain.usecase.KakaoLoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginViewModel@Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase
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
    companion object {
        val TAG = this.javaClass.simpleName.toString()
    }
}