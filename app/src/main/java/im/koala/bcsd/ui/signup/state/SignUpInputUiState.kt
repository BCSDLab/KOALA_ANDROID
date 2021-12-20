package im.koala.bcsd.ui.signup.state

import im.koala.domain.util.checkemail.EmailCheckResult
import im.koala.domain.util.checkid.IdCheckResult
import im.koala.domain.util.checknickname.NicknameCheckResult
import im.koala.domain.util.checkpassword.PasswordCheckResult

data class SignUpInputUiState(
    val id: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val email: String = "",
    val nickname: String = "",
    val idErrorCode: IdCheckResult = IdCheckResult.OK,
    val passwordErrorCode: PasswordCheckResult = PasswordCheckResult.OK,
    val isPasswordConfirmMatch: Boolean = true,
    val emailErrorCode: EmailCheckResult = EmailCheckResult.OK,
    val nicknameErrorCode: NicknameCheckResult = NicknameCheckResult.OK
)
