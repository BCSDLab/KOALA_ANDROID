package im.koala.bcsd.ui.login

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.ui.BaseViewModel
import im.koala.domain.model.CommonResponse
import im.koala.domain.state.ApiResponse
import im.koala.domain.usecase.GetDeviceTokenUseCase
import im.koala.domain.usecase.KakaoLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel@Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val getDeviceTokenUseCase: GetDeviceTokenUseCase
) : BaseViewModel() {
    private var context: Context? = null

    private val _uiState: MutableState<DeviceTokenUiState> = mutableStateOf(DeviceTokenUiState())
    val uiState: State<DeviceTokenUiState> get() = _uiState

    /* 구독 상태 객체 */
    private val _snsLoginState = MutableStateFlow<ApiResponse>(ApiResponse.Uninitialized)
    val snsLoginState: StateFlow<ApiResponse> get() = _snsLoginState

    init {

        viewModelScope.launch {
            snsLoginState.collectLatest {
                when (it) {
                    is ApiResponse.Success<*> -> {
                        _uiState.value = _uiState.value.copy(goToMainActivity = true)
                    }
                    is ApiResponse.Fail<*> -> {
                        val response = (snsLoginState as ApiResponse.Fail<*>).data as CommonResponse
                        _uiState.value = _uiState.value.copy(errorMesage = response.errorMessage!!)
                    }
                }
            }
        }
    }
    fun onClickKakaoLoginButton() {
        _snsLoginState.value = ApiResponse.Loading
        kakaoToken().zip(getDeviceTokenUseCase()) { accessToken, deviceToken ->
            return@zip kakaoLoginUseCase(accessToken = accessToken, deviceToken = deviceToken)
        }.onEach {
            _snsLoginState.value = it
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun setActivityContext(context: Context?) {
        this.context = context
    }
    fun kakaoToken(): Flow<String> = callbackFlow {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                close()
            } else if (token != null) {
                this.trySend(token.accessToken).isSuccess
            }
        }
        context?.let {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(it)) {
                UserApiClient.instance.loginWithKakaoTalk(it, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(it, callback = callback)
            }
        }
        awaitClose { _uiState.value = _uiState.value.copy(errorMesage = "") }
    }

    companion object {
        val TAG = "LoginViewModel"
    }
}
data class DeviceTokenUiState(
    val goToMainActivity: Boolean = false,
    val errorMesage: String = ""
)