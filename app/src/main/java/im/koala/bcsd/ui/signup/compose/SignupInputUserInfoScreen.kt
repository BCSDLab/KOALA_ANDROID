package im.koala.bcsd.ui.signup.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.signup.state.SignUpInputUiState
import im.koala.domain.util.checkemail.EmailCheckResult
import im.koala.domain.util.checkid.IdCheckResult
import im.koala.domain.util.checknickname.NicknameCheckResult
import im.koala.domain.util.checkpassword.PasswordCheckResult
import im.koala.domain.util.checkpassword.PasswordCheckStatus
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SignupInputUserInfoScreen(
    signUpInputUiState: SignUpInputUiState,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onNicknameChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    fun animateScrollToItem(index: Int) {
        coroutineScope.launch {
            listState.animateScrollToItem(index = index)
        }
    }

    LazyColumn(
        state = listState
    ) {
        items(1) {
            SignupSubtitle(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 24.dp,
                    bottom = 24.dp
                ),
                text1 = stringResource(R.string.signup_subtitle_user_info),
                text2 = stringResource(R.string.signup_subtitle_user_info_description)
            )
        }

        items(5) { index ->
            when (index) {
                0 -> SignupTextFieldWithErrorMessage(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            animateScrollToItem(0)
                        }
                    },
                    value = signUpInputUiState.id,
                    onValueChange = onIdChanged,
                    isError = signUpInputUiState.idCheckResult != IdCheckResult.OK,
                    errorMessage = idErrorMessage(idCheckResult = signUpInputUiState.idCheckResult),
                    hint = stringResource(R.string.signup_input_hint_id),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )

                1 -> SignupPasswordTextFieldWithErrorMessage(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            animateScrollToItem(1)
                        }
                    },
                    value = signUpInputUiState.password,
                    onValueChange = onPasswordChanged,
                    isError = signUpInputUiState.passwordCheckResult != PasswordCheckStatus.OK,
                    errorMessage = passwordErrorMessage(passwordCheckResult = signUpInputUiState.passwordCheckResult),
                    hint = stringResource(R.string.signup_input_hint_password),
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                )

                2 -> SignupPasswordTextFieldWithErrorMessage(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            animateScrollToItem(2)
                        }
                    },
                    value = signUpInputUiState.passwordConfirm,
                    onValueChange = onPasswordConfirmChanged,
                    isError = !signUpInputUiState.isPasswordConfirmMatch,
                    errorMessage = stringResource(id = R.string.signup_input_error_password_not_match),
                    hint = stringResource(R.string.signup_input_hint_password_2),
                    imeAction = ImeAction.Next,
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                )

                3 -> SignupTextFieldWithErrorMessage(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            animateScrollToItem(3)
                        }
                    },
                    value = signUpInputUiState.email,
                    onValueChange = onEmailChanged,
                    isError = signUpInputUiState.emailCheckResult != EmailCheckResult.OK,
                    errorMessage = emailErrorMessage(emailCheckResult = signUpInputUiState.emailCheckResult),
                    hint = stringResource(R.string.signup_input_hint_email),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )

                4 -> SignupTextFieldWithErrorMessage(
                    modifier = Modifier.onFocusChanged {
                        if (it.isFocused) {
                            animateScrollToItem(4)
                        }
                    },
                    value = signUpInputUiState.nickname,
                    onValueChange = onNicknameChanged,
                    isError = signUpInputUiState.nicknameCheckResult != NicknameCheckResult.OK,
                    errorMessage = nicknameErrorMessage(nicknameCheckResult = signUpInputUiState.nicknameCheckResult),
                    hint = stringResource(R.string.signup_input_hint_nickname),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            softwareKeyboardController?.hide()
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }
        }
    }
}

@Composable
private fun idErrorMessage(idCheckResult: IdCheckResult) = when (idCheckResult) {
    IdCheckResult.IdDuplicatedError -> stringResource(id = R.string.signup_input_error_id_duplicated)
    IdCheckResult.NoSuchInputError -> stringResource(id = R.string.signup_input_error_id_no_input)
    IdCheckResult.OK -> ""
}

@Composable
private fun passwordErrorMessage(passwordCheckResult: PasswordCheckResult): String {
    return when {
        //비밀번호 입력 없음
        PasswordCheckStatus.NoSuchInputError in passwordCheckResult -> {
            stringResource(id = R.string.signup_input_error_password_no_input)
        }
        //사용 불가능 문자(이모티콘 등) 포함
        PasswordCheckStatus.NotSupportCharactersError in passwordCheckResult -> stringResource(
            id = R.string.signup_password_error_not_support_character
        )
        //15자 이상
        PasswordCheckStatus.TooLongCharactersError in passwordCheckResult -> {
            stringResource(id = R.string.signup_password_error_length_upper_15)
        }
        //8자 이하
        PasswordCheckStatus.TooShortCharactersError in passwordCheckResult -> {
            stringResource(id = R.string.signup_password_error_length_lower_8)
        }
        //영문자 없음
        PasswordCheckStatus.NotContainsEnglishError in passwordCheckResult -> stringResource(
            id = R.string.signup_password_error_not_contains,
            stringResource(id = R.string.english)
        )
        //숫자 없음
        PasswordCheckStatus.NotContainsNumberError in passwordCheckResult -> stringResource(
            id = R.string.signup_password_error_not_contains,
            stringResource(id = R.string.number)
        )
        //특수문자 없음
        PasswordCheckStatus.NotContainsSpecialCharacterError in passwordCheckResult -> stringResource(
            id = R.string.signup_password_error_not_contains,
            stringResource(id = R.string.special)
        )
        //영어 + 숫자 없음
        PasswordCheckStatus.NotContainsEnglishError + PasswordCheckStatus.NotContainsNumberError in passwordCheckResult -> stringResource(
            id = R.string.signup_password_error_not_contains,
            "${stringResource(id = R.string.english)}, ${stringResource(id = R.string.number)}"
        )
        //숫자 + 특수문자 없음
        PasswordCheckStatus.NotContainsNumberError + PasswordCheckStatus.NotContainsSpecialCharacterError in passwordCheckResult -> stringResource(
            id = R.string.signup_password_error_not_contains,
            "${stringResource(id = R.string.number)}, ${stringResource(id = R.string.special)}"
        )
        //영어 + 특수문자 없음
        PasswordCheckStatus.NotContainsSpecialCharacterError + PasswordCheckStatus.NotContainsEnglishError in passwordCheckResult -> stringResource(
            id = R.string.signup_password_error_not_contains,
            "${stringResource(id = R.string.english)}, ${stringResource(id = R.string.special)}"
        )
        else -> ""
    }
}

@Composable
private fun emailErrorMessage(emailCheckResult: EmailCheckResult) = when (emailCheckResult) {
    EmailCheckResult.EmailDuplicatedError -> stringResource(id = R.string.signup_input_error_email_duplicated)
    EmailCheckResult.NoSuchInputError -> stringResource(id = R.string.signup_input_error_email_no_input)
    EmailCheckResult.NotEmailFormatError -> stringResource(id = R.string.signup_input_error_email_format_not_match)
    EmailCheckResult.OK -> ""
}

@Composable
private fun nicknameErrorMessage(nicknameCheckResult: NicknameCheckResult) =
    when (nicknameCheckResult) {
        NicknameCheckResult.NicknameDuplicatedError -> stringResource(id = R.string.signup_input_error_nickname_duplicated)
        NicknameCheckResult.NoSuchInputError -> stringResource(id = R.string.signup_input_error_nickname_no_input)
        NicknameCheckResult.OK -> ""
    }