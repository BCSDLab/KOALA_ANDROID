package im.koala.bcsd.ui.login

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.ui.BaseViewModel
import im.koala.data.constants.KAKAO_TOKEN
import im.koala.data.constants.NAVER_TOKEEN
import im.koala.domain.constants.GOOGLE
import im.koala.domain.constants.KAKAO
import im.koala.domain.constants.NAVER
import im.koala.domain.model.CommonResponse
import im.koala.domain.state.Result
import im.koala.domain.usecase.GetFCMTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val snsLoginUseCase: SnsLoginUseCase,
    private val getFCMTokenUseCase: GetFCMTokenUseCase,
) : BaseViewModel() {
    private val _uiState: MutableState<DeviceTokenUiState> = mutableStateOf(DeviceTokenUiState())
    val uiState: State<DeviceTokenUiState> = _uiState
    val snsTokenFlow = MutableSharedFlow<String>(replay = 0)
    private val _uiEvent = MutableSharedFlow<LoginViewUIEvent>(replay = 0)
    val uiEvent: SharedFlow<LoginViewUIEvent>
        get() = _uiEvent

    var snsType: String = ""

    val naverLoginCallback = object : OAuthLoginCallback {
        override fun onError(errorCode: Int, message: String) {
        }

        override fun onFailure(httpStatus: Int, message: String) {
        }

        override fun onSuccess() {
            Hawk.put(NAVER_TOKEEN, NaverIdLoginSDK.getAccessToken())
            viewModelScope.launch {
                Log.e("token", NaverIdLoginSDK.getAccessToken()!!)
                snsTokenFlow.emit(NaverIdLoginSDK.getAccessToken()!!)
            }
        }
    }

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
        } else if (token != null) {
            Hawk.put(KAKAO_TOKEN, token.accessToken)
            viewModelScope.launch {
                snsTokenFlow.emit(token.accessToken)
            }
        }
    }

    /* 구독 상태 객체 */
    private var snsLoginState: MutableStateFlow<Result> = MutableStateFlow(Result.Uninitialized)

    init {
        viewModelScope.launch(vmExceptionHandler) {
            snsLoginState.collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        _uiEvent.emit(LoginViewUIEvent.GoToMainActivity)
                        // _uiState.value = _uiState.value.copy(goToMainActivity = true)
                    }
                    is Result.Fail<*> -> {

                        val response = it.data as CommonResponse
                        _uiEvent.emit(LoginViewUIEvent.ShowErrorMessage(response.errorMessage!!))
                        // _uiState.value = _uiState.value.copy(errorMesage = response.errorMessage!!)
                    }
                }
            }
        }
        snsTokenFlow.zip(getFCMTokenUseCase()) { accessToken, FCMToken ->
            Log.e("sdf", "$snsType / $accessToken / $FCMToken")
            return@zip snsLoginUseCase(
                snsType = snsType,
                accessToken = accessToken,
                deviceToken = FCMToken
            )
        }.onEach {
            it.collectLatest { snsLoginState.emit(it) }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
    fun onClickSnsLoginButton(type: String) {
        viewModelScope.launch {
            when (type) {
                KAKAO -> _uiEvent.emit(LoginViewUIEvent.ProceedKakaoLogin)
                NAVER -> _uiEvent.emit(LoginViewUIEvent.ProceedNaverLogin)
                GOOGLE -> _uiEvent.emit(LoginViewUIEvent.ProceedGoogleLogin)
            }
        }
    }

    companion object {
        val TAG = "LoginViewModel"
    }
}

sealed class LoginViewUIEvent {
    object GoToMainActivity : LoginViewUIEvent()
    data class ShowErrorMessage(val message: String) : LoginViewUIEvent()
    object ProceedNaverLogin : LoginViewUIEvent()
    object ProceedGoogleLogin : LoginViewUIEvent()
    object ProceedKakaoLogin : LoginViewUIEvent()
}
data class DeviceTokenUiState(
    val goToMainActivity: Boolean = false,
    val errorMessage: String = ""
)