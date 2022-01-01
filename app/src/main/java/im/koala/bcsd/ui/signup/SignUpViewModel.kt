package im.koala.bcsd.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.ui.signup.state.SignUpInputUiState
import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.usecase.signup.SignUpCheckEmailUseCase
import im.koala.domain.usecase.signup.SignUpCheckIdUseCase
import im.koala.domain.usecase.signup.SignUpCheckNicknameUseCase
import im.koala.domain.usecase.signup.SignUpCheckPasswordConfirmUseCase
import im.koala.domain.usecase.signup.SignUpCheckPasswordUseCase
import im.koala.domain.usecase.signup.SignUpUseCase
import im.koala.domain.util.checkemail.EmailCheckResult
import im.koala.domain.util.checkid.IdCheckResult
import im.koala.domain.util.checknickname.NicknameCheckResult
import im.koala.domain.util.checkpassword.PasswordCheckStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpCheckIdUseCase: SignUpCheckIdUseCase,
    private val signUpCheckPasswordUseCase: SignUpCheckPasswordUseCase,
    private val signUpCheckPasswordConfirmUseCase: SignUpCheckPasswordConfirmUseCase,
    private val signUpCheckEmailUseCase: SignUpCheckEmailUseCase,
    private val signUpCheckNicknameUseCase: SignUpCheckNicknameUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private var checkIdJob: Job? = null
    private var checkEmailJob: Job? = null
    private var checkNicknameJob: Job? = null

    var signUpValueUiState by mutableStateOf(SignUpInputUiState())
        private set

    var signUpResult by mutableStateOf<SignUpResult?>(null)
        private set

    fun setId(id: String) {
        signUpValueUiState = signUpValueUiState.copy(id = id)
        checkId()
    }

    fun setPassword(password: String) {
        signUpValueUiState = signUpValueUiState.copy(password = password)
        checkPassword()
        checkPasswordMatch()
    }

    fun setPasswordConfirm(passwordConfirm: String) {
        signUpValueUiState = signUpValueUiState.copy(passwordConfirm = passwordConfirm)
        checkPasswordMatch()
    }

    fun setEmail(email: String) {
        signUpValueUiState = signUpValueUiState.copy(email = email)
        checkEmail()
    }

    fun setNickname(nickname: String) {
        signUpValueUiState = signUpValueUiState.copy(nickname = nickname)
        checkNickname()
    }

    private fun checkId() {
        checkIdJob?.cancel()
        checkIdJob = viewModelScope.launch {
            signUpValueUiState = signUpValueUiState.copy(
                idCheckResult = IdCheckResult.Loading
            )

            signUpValueUiState = signUpValueUiState.copy(
                idCheckResult = signUpCheckIdUseCase(signUpValueUiState.id)
            )
        }
    }

    private fun checkPassword() {
        signUpValueUiState = signUpValueUiState.copy(
            passwordCheckResult = signUpCheckPasswordUseCase(signUpValueUiState.password)
        )
    }

    private fun checkPasswordMatch() {
        signUpValueUiState = signUpValueUiState.copy(
            isPasswordConfirmMatch = signUpCheckPasswordConfirmUseCase(
                signUpValueUiState.password,
                signUpValueUiState.passwordConfirm
            )
        )
    }

    private fun checkEmail() {
        checkEmailJob?.cancel()
        checkEmailJob = viewModelScope.launch {
            signUpValueUiState = signUpValueUiState.copy(
                emailCheckResult = EmailCheckResult.Loading
            )
            signUpValueUiState = signUpValueUiState.copy(
                emailCheckResult = signUpCheckEmailUseCase(signUpValueUiState.email)
            )
        }
    }

    private fun checkNickname() {
        checkNicknameJob?.cancel()
        checkNicknameJob = viewModelScope.launch {
            signUpValueUiState = signUpValueUiState.copy(
                nicknameCheckResult = NicknameCheckResult.Loading
            )
            signUpValueUiState = signUpValueUiState.copy(
                nicknameCheckResult = signUpCheckNicknameUseCase(signUpValueUiState.nickname)
            )
        }
    }

    fun signUp() {
        viewModelScope.launch {
            checkIdJob?.join()
            checkNicknameJob?.join()
            checkEmailJob?.join()

            with(signUpValueUiState) {
                if (idCheckResult == IdCheckResult.OK &&
                    passwordCheckResult == PasswordCheckStatus.OK &&
                    isPasswordConfirmMatch &&
                    emailCheckResult == EmailCheckResult.OK &&
                    nicknameCheckResult == NicknameCheckResult.OK
                ) {
                    signUpResult = signUpUseCase(
                        id, password, email, nickname
                    )
                }
            }
        }
    }
}