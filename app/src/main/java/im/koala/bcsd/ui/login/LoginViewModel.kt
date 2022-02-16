package im.koala.bcsd.ui.login

import android.app.Activity
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.R
import im.koala.bcsd.ui.BaseViewModel
import im.koala.domain.constants.GOOGLE
import im.koala.domain.constants.KAKAO
import im.koala.domain.constants.NAVER
import im.koala.domain.model.CommonResponse
import im.koala.domain.state.Result
import im.koala.domain.usecase.GetDeviceTokenUseCase
import im.koala.domain.usecase.GooglePostAccessTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val snsLoginUseCase: SnsLoginUseCase,
    private val getDeviceTokenUseCase: GetDeviceTokenUseCase,
    private val googlePostAccessTokenUseCase: GooglePostAccessTokenUseCase
) : BaseViewModel() {
    private val _uiState: MutableState<DeviceTokenUiState> = mutableStateOf(DeviceTokenUiState())
    val uiState: State<DeviceTokenUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<LoginViewUIEvent>(replay = 0)
    val uiEvent: SharedFlow<LoginViewUIEvent>
        get() = _uiEvent

    /* 구독 상태 객체 */
    private var snsLoginState: MutableStateFlow<Result> = MutableStateFlow(Result.Uninitialized)

    init {
        viewModelScope.launch(vmExceptionHandler) {
            snsLoginState.collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        _uiState.value = _uiState.value.copy(goToMainActivity = true)
                    }
                    is Result.Fail<*> -> {
                        val response = it.data as CommonResponse
                        _uiState.value = _uiState.value.copy(errorMesage = response.errorMessage!!)
                    }
                }
            }
        }
    }

    fun onClickKakaoLoginButton() {
        kakaoToken().zip(getDeviceTokenUseCase()) { accessToken, deviceToken ->
            return@zip snsLoginUseCase(
                snsType = KAKAO,
                accessToken = accessToken,
                deviceToken = deviceToken
            )
        }.onEach {
            it.collectLatest { snsLoginState.emit(it) }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun kakaoToken(): Flow<String> = callbackFlow {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                close()
            } else if (token != null) {
                trySend(token.accessToken)
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

    fun googleLogin(authCode: String?) {
        googleToken(authCode).zip(getDeviceTokenUseCase()) { accessToken, deviceToken ->
            return@zip snsLoginUseCase(
                snsType = GOOGLE,
                accessToken = accessToken,
                deviceToken = deviceToken
            )
        }.onEach {
            it.collectLatest { snsLoginState.emit(it) }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun googleToken(authCode: String?): Flow<String> = callbackFlow {
        context?.let {
            if (authCode != null) {
                val token = googlePostAccessTokenUseCase(
                    clientId = it.applicationContext.getString(R.string.google_web_client_id),
                    clientSecret = it.applicationContext.getString(R.string.google_web_client_secret),
                    authCode = authCode
                )
                if (token != null) {
                    trySend(token)
                } else {
                    _uiState.value =
                        _uiState.value.copy(errorMesage = it.getString(R.string.google_login_fail))
                    close()
                }
            } else {
                _uiState.value =
                    _uiState.value.copy(errorMesage = it.getString(R.string.google_login_fail))
                close()
            }
        }
        awaitClose { _uiState.value = _uiState.value.copy(errorMesage = "") }
    }

    fun onClickNaverLoginButton() {
        viewModelScope.launch {
            _uiEvent.emit(LoginViewUIEvent.ProceedNaverLogin)
        }
    }

    fun proceedNaverLogin() {
        naverToken().zip(getDeviceTokenUseCase()) { accessToken, deviceToken ->
            return@zip snsLoginUseCase(
                snsType = NAVER,
                accessToken = accessToken,
                deviceToken = deviceToken
            )
        }.onEach {
            it.collectLatest { snsLoginState.emit(it) }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun naverToken(): Flow<String> = callbackFlow {
        context?.let {
            val oAuthLoginHandler = object : OAuthLoginHandler(Looper.getMainLooper()) {
                override fun run(success: Boolean) {
                    if (success) {
                        trySend(OAuthLogin.getInstance().getAccessToken(it))
                    } else {
                        _uiState.value =
                            _uiState.value.copy(errorMesage = it.getString(R.string.naver_login_fail))
                        close()
                    }
                }
            }
            when (OAuthLogin.getInstance().getState(it)) {
                OAuthLoginState.NEED_INIT -> {
                    OAuthLogin.getInstance().init(
                        it,
                        it.applicationContext.resources.getString(R.string.naver_client_id),
                        it.applicationContext.resources.getString(R.string.naver_client_secret),
                        it.getString(R.string.app_name)
                    )
                    OAuthLogin.getInstance()
                        .startOauthLoginActivity(it as Activity, oAuthLoginHandler)
                }
                OAuthLoginState.NEED_LOGIN -> {
                    OAuthLogin.getInstance()
                        .startOauthLoginActivity(it as Activity, oAuthLoginHandler)
                }
                OAuthLoginState.NEED_REFRESH_TOKEN -> {
                    val accessToken = OAuthLogin.getInstance().refreshAccessToken(it)
                    if (accessToken != null) {
                        trySend(accessToken)
                    } else {
                        OAuthLogin.getInstance()
                            .startOauthLoginActivity(it as Activity, oAuthLoginHandler)
                    }
                }
                OAuthLoginState.OK -> {
                    val oAuthLoginPreferenceManager = OAuthLoginPreferenceManager(it)
                    val accessToken = oAuthLoginPreferenceManager.accessToken
                    if (accessToken != null) {
                        trySend(accessToken)
                    } else {
                        OAuthLogin.getInstance()
                            .startOauthLoginActivity(it as Activity, oAuthLoginHandler)
                    }
                }
            }
        }
        awaitClose { _uiState.value = _uiState.value.copy(errorMesage = "") }
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
}

data class DeviceTokenUiState(
    val goToMainActivity: Boolean = false,
    val errorMessage: String = ""
)