package im.koala.bcsd.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import im.koala.bcsd.ui.signup.state.SignUpInputUiState
import im.koala.domain.usecase.*

class SignupViewModel(
    private val signUpCheckIdUseCase: SignUpCheckIdUseCase,
    private val signUpCheckPasswordUseCase: SignUpCheckPasswordUseCase,
    private val signUpCheckPasswordConfirmUseCase: SignUpCheckPasswordConfirmUseCase,
    private val signUpCheckEmailUseCase: SignUpCheckEmailUseCase,
    private val signUpCheckNicknameUseCase: SignUpCheckNicknameUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    var signUpValueUiState by mutableStateOf(SignUpInputUiState())
        private set

    var signupCompleted by mutableStateOf(false)
        private set

    fun setId(id: String) {
        signUpValueUiState = signUpValueUiState.copy(id = id)
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
    }

    fun setNickname(nickname: String) {
        signUpValueUiState = signUpValueUiState.copy(nickname = nickname)
    }

    fun checkId() {
        signUpValueUiState = signUpValueUiState.copy(
            idCheckResult = signUpCheckIdUseCase(signUpValueUiState.id)
        )
    }

    fun checkPassword() {
        signUpValueUiState = signUpValueUiState.copy(
            passwordCheckResult = signUpCheckPasswordUseCase(signUpValueUiState.password)
        )
    }

    fun checkPasswordMatch() {
        signUpValueUiState = signUpValueUiState.copy(
            isPasswordConfirmMatch = signUpCheckPasswordConfirmUseCase(
                signUpValueUiState.password,
                signUpValueUiState.passwordConfirm
            )
        )
    }

    fun checkEmail() {
        signUpValueUiState = signUpValueUiState.copy(
            emailCheckResult = signUpCheckEmailUseCase(signUpValueUiState.email)
        )
    }

    fun checkNickname() {
        signUpValueUiState = signUpValueUiState.copy(
            nicknameCheckResult = signUpCheckNicknameUseCase(signUpValueUiState.nickname)
        )
    }

    fun signUp() {
        checkEmail()
        checkNickname()
        checkId()
        with(signUpValueUiState) {
            signupCompleted = signUpUseCase(
                id, password, email, nickname
            )
        }
    }
}