package im.koala.bcsd.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.ui.BaseViewModel
import im.koala.domain.constants.GOOGLE
import im.koala.domain.constants.KAKAO
import im.koala.domain.constants.NAVER
import im.koala.domain.model.CommonResponse
import im.koala.domain.state.Result
import im.koala.domain.usecase.GetFCMTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase
import im.koala.domain.usecase.user.GetAutoLoginStateUseCase
import im.koala.domain.usecase.user.LoginWithIdPasswordUseCase
import im.koala.domain.usecase.user.LoginWithoutIdPasswordUseCase
import im.koala.domain.usecase.user.SetAutoLoginStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
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
    private val loginWithIdPasswordUseCase: LoginWithIdPasswordUseCase,
    private val loginWithoutIdPasswordUseCase: LoginWithoutIdPasswordUseCase,
    private val getAutoLoginStateUseCase: GetAutoLoginStateUseCase,
    private val setAutoLoginStateUseCase: SetAutoLoginStateUseCase
) : BaseViewModel() {
    private val _uiState: MutableState<DeviceTokenUiState> = mutableStateOf(DeviceTokenUiState())
    val uiState: State<DeviceTokenUiState> = _uiState
    val loginTokenFlow = MutableSharedFlow<String>(replay = 0)
    private val _uiEvent = MutableSharedFlow<LoginViewUIEvent>(replay = 0)
    val uiEvent: SharedFlow<LoginViewUIEvent>
        get() = _uiEvent

    var snsType: String = ""

    val naverLoginCallback = object : OAuthLoginCallback {
        override fun onError(errorCode: Int, message: String) {
            viewModelScope.launch { _uiEvent.emit(LoginViewUIEvent.ShowErrorMessage(message)) }
        }

        override fun onFailure(httpStatus: Int, message: String) {
            viewModelScope.launch { _uiEvent.emit(LoginViewUIEvent.ShowErrorMessage(message)) }
        }

        override fun onSuccess() {
            viewModelScope.launch {
                loginTokenFlow.emit(NaverIdLoginSDK.getAccessToken()!!)
            }
        }
    }

    val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
        } else if (token != null) {
            viewModelScope.launch {
                loginTokenFlow.emit(token.accessToken)
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
                    }
                    is Result.Fail<*> -> {

                        val response = it.data as CommonResponse
                        _uiEvent.emit(LoginViewUIEvent.ShowErrorMessage(response.errorMessage!!))
                    }
                }
            }
        }
        loginTokenFlow.zip(getFCMTokenUseCase()) { accessToken, FCMToken ->
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

    fun emitSnsToken(token: String) {
        viewModelScope.launch {
            loginTokenFlow.emit(token)
        }
    }
    fun login(
        autoLogin: Boolean,
        id: String,
        password: String
    ) = viewModelScope.launch(vmExceptionHandler) {
        isLoading = true
        getFCMTokenUseCase().flatMapLatest {
            loginWithIdPasswordUseCase(
                deviceToken = it,
                id = id,
                password = password
            )
        }.collectLatest {
            isLoading = false
            snsLoginState.emit(it)
        }
    }

    fun loginNonMember(autoLogin: Boolean) = viewModelScope.launch(vmExceptionHandler) {
        isLoading = true
        getFCMTokenUseCase().flatMapLatest {
            loginWithoutIdPasswordUseCase(
                deviceToken = it
            )
        }.collectLatest {
            isLoading = false
            snsLoginState.emit(it)
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