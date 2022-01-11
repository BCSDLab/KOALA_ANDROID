package im.koala.bcsd.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
        } else if (token != null) {
            executeSnsLogin(KAKAO, token.accessToken)
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