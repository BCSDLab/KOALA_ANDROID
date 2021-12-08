package im.koala.bcsd.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import im.koala.bcsd.util.PasswordChecker
import im.koala.bcsd.util.isEmail
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val ID_OK = 0
const val ID_IS_DUPLICATED = 1

const val EMAIL_OK = 0
const val EMAIL_IS_NOT_EMAIL_FORMAT = 1

const val NICKNAME_OK = 0
const val NICKNAME_IS_DUPLICATED = 1

class SignupViewModel : ViewModel() {
    private val _id = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _password2 = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()
    private val _nickname = MutableLiveData<String>()

    private val _idErrorCode = MutableLiveData<Int>()
    private val _passwordErrorCode = MutableLiveData<Int>()
    private val _passwordMatch = MutableLiveData<Boolean>()
    private val _emailErrorCode = MutableLiveData<Int>()
    private val _nicknameErrorCode = MutableLiveData<Int>()

    private val _signupCompleted = MutableLiveData<Boolean>()

    val id: LiveData<String> get() = _id
    val password: LiveData<String> get() = _password
    val password2: LiveData<String> get() = _password2
    val email: LiveData<String> get() = _email
    val nickname: LiveData<String> get() = _nickname

    val idErrorCode: LiveData<Int> get() = _idErrorCode
    val passwordErrorCode: LiveData<Int> get() = _passwordErrorCode
    val passwordMatch: LiveData<Boolean> get() = _passwordMatch
    val emailErrorCode: LiveData<Int> get() = _emailErrorCode
    val nicknameErrorCode: LiveData<Int> get() = _nicknameErrorCode

    val signupCompleted: LiveData<Boolean> get() = _signupCompleted

    private var idJob: Job? = null
    private var emailJob: Job? = null
    private var nicknameJob: Job? = null

    fun setId(id: String) {
        _id.value = id
    }

    fun setPassword(password: String) {
        _password.value = password
        checkPassword()
    }

    fun setPassword2(password2: String) {
        _password2.value = password2
        checkPasswordMatch()
    }

    fun setEmail(email: String) {
        _email.value = email
        checkEmailFormat()
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun startCheckId() {
        idJob?.let {
            if (it.isActive) it.cancel()
        }
        idJob = viewModelScope.launch {
            _idErrorCode.value = when {
                // TODO id 글자 수 체크
                // TODO id 중복체크
                else -> ID_OK
            }
        }
    }

    fun checkPassword() {
        _passwordErrorCode.value = PasswordChecker.checkPassword(password.value ?: "")
    }

    fun checkPasswordMatch() {
        _passwordMatch.value = password.value == password2.value
    }

    fun checkEmailFormat() {
        _emailErrorCode.value =
            if (email.value?.isEmail() == false) EMAIL_IS_NOT_EMAIL_FORMAT else EMAIL_OK
    }

    fun startCheckEmail() {
        emailJob?.let {
            if (it.isActive) it.cancel()
        }
        emailJob = viewModelScope.launch {
            _emailErrorCode.value = when {
                email.value?.isEmail() == false -> EMAIL_IS_NOT_EMAIL_FORMAT // email 형식 체크
                // TODO email 중복체크
                else -> EMAIL_OK
            }
        }
    }

    fun startCheckNickname() {
        nicknameJob?.let {
            if (it.isActive) it.cancel()
        }
        nicknameJob = viewModelScope.launch {
            _nicknameErrorCode.value = when {
                // TODO nickname 중복 체크
                else -> NICKNAME_OK
            }
        }
    }

    suspend fun signUp() {
        startCheckEmail()
        startCheckId()
        startCheckNickname()

        idJob?.let { it.join() }
        emailJob?.let { it.join() }
        nicknameJob?.let { it.join() }

        // TODO: Signup gogo

        if (idErrorCode.value == ID_OK &&
            passwordErrorCode.value == PasswordChecker.PASSWORD_OK &&
            passwordMatch.value == true &&
            emailErrorCode.value == EMAIL_OK &&
            nicknameErrorCode.value == NICKNAME_OK
        ) _signupCompleted.value = true
    }
}