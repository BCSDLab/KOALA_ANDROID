package im.koala.bcsd.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import im.koala.bcsd.ui.signup.state.SignUpInputUiState
import im.koala.domain.usecase.SignUpCheckEmailUseCase
import im.koala.domain.usecase.SignUpCheckIdUseCase
import im.koala.domain.usecase.SignUpCheckNicknameUseCase
import im.koala.domain.usecase.SignUpCheckPasswordConfirmUseCase
import im.koala.domain.usecase.SignUpCheckPasswordUseCase
import im.koala.domain.usecase.SignUpUseCase
import im.koala.domain.util.checkemail.EmailCheckResult
import im.koala.domain.util.checkid.IdCheckResult
import im.koala.domain.util.checknickname.NicknameCheckResult
import im.koala.domain.util.checkpassword.PasswordCheckStatus
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
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
        signUpValueUiState = signUpValueUiState.copy(
            idCheckResult = signUpCheckIdUseCase(signUpValueUiState.id)
        )
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
        signUpValueUiState = signUpValueUiState.copy(
            emailCheckResult = signUpCheckEmailUseCase(signUpValueUiState.email)
        )
    }

    private fun checkNickname() {
        signUpValueUiState = signUpValueUiState.copy(
            nicknameCheckResult = signUpCheckNicknameUseCase(signUpValueUiState.nickname)
        )
    }

    fun signUp() {
        with(signUpValueUiState) {
            if (idCheckResult == IdCheckResult.OK &&
                passwordCheckResult == PasswordCheckStatus.OK &&
                isPasswordConfirmMatch &&
                emailCheckResult == EmailCheckResult.OK &&
                nicknameCheckResult == NicknameCheckResult.OK
            ) {
                signupCompleted = signUpUseCase(
                    id, password, email, nickname
                )
            }
        }
    }
}