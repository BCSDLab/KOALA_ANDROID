package im.koala.bcsd.ui.signup.state

data class SignUpInputUiState(
    val id: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val email: String = "",
    val nickname: String = ""
)
