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
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SignupInputUserInfoScreen(
    signUpInputUiState: SignUpInputUiState,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPasswordConfirmChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onNicknameChanged: (String) -> Unit,
    onFocusChanged: () -> Unit
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
                            onFocusChanged()
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
                            onFocusChanged()
                        }
                    },
                    value = signUpInputUiState.password,
                    onValueChange = onPasswordChanged,
                    isError = signUpInputUiState.passwordCheckResult != PasswordCheckResult.OK,
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
                            onFocusChanged()
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
                            onFocusChanged()
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
                            onFocusChanged()
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
private fun passwordErrorMessage(passwordCheckResult: PasswordCheckResult) =
    when (passwordCheckResult) {
        in PasswordCheckResult.NoSuchInputError -> stringResource(id = R.string.signup_input_error_password_no_input)
        in PasswordCheckResult.NotContainsEnglishError -> stringResource(
            id = R.string.signup_password_error_not_contains,
            stringResource(id = R.string.english)
        )
        in PasswordCheckResult.NotContainsNumberError -> stringResource(
            id = R.string.signup_password_error_not_contains,
            stringResource(id = R.string.number)
        )
        in PasswordCheckResult.NotContainsSpecialCharacterError -> stringResource(
            id = R.string.signup_password_error_not_contains,
            stringResource(id = R.string.special)
        )
        in PasswordCheckResult.NotContainsEnglishError + PasswordCheckResult.NotContainsNumberError -> stringResource(
            id = R.string.signup_password_error_not_contains,
            "${stringResource(id = R.string.english)}, ${stringResource(id = R.string.number)}"
        )
        in PasswordCheckResult.NotContainsNumberError + PasswordCheckResult.NotContainsSpecialCharacterError -> stringResource(
            id = R.string.signup_password_error_not_contains,
            "${stringResource(id = R.string.number)}, ${stringResource(id = R.string.special)}"
        )
        in PasswordCheckResult.NotContainsSpecialCharacterError + PasswordCheckResult.NotContainsEnglishError -> stringResource(
            id = R.string.signup_password_error_not_contains,
            "${stringResource(id = R.string.english)}, ${stringResource(id = R.string.special)}"
        )
        in PasswordCheckResult.TooLongCharactersError -> stringResource(id = R.string.signup_password_error_length_upper_15)
        in PasswordCheckResult.TooShortCharactersError -> stringResource(id = R.string.signup_password_error_length_lower_8)
        else -> ""
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