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
    val idCheckResult: IdCheckResult = IdCheckResult.OK,
    val passwordCheckResult: PasswordCheckResult = PasswordCheckResult.OK,
    val isPasswordConfirmMatch: Boolean = true,
    val emailCheckResult: EmailCheckResult = EmailCheckResult.OK,
    val nicknameCheckResult: NicknameCheckResult = NicknameCheckResult.OK
)
