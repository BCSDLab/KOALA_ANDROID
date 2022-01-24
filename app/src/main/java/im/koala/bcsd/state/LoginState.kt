package im.koala.bcsd.state

sealed class LoginState {
    object NeedLogin : LoginState()
    object Success : LoginState()
    data class Fail(var errorMessage: String?) : LoginState()
}