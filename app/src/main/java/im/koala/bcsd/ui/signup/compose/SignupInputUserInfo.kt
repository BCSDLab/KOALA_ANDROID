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
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SignupInputUserInfo(
    id: String,
    password: String,
    password2: String,
    email: String,
    nickname: String,
    idErrorMessage: String?,
    passwordErrorMessage: String?,
    password2ErrorMessage: String?,
    emailErrorMessage: String?,
    nicknameErrorMessage: String?,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onPassword2Changed: (String) -> Unit,
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
                    value = id,
                    onValueChange = onIdChanged,
                    isError = idErrorMessage != null,
                    errorMessage = idErrorMessage ?: "",
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
                    value = password,
                    onValueChange = onPasswordChanged,
                    isError = passwordErrorMessage != null,
                    errorMessage = passwordErrorMessage ?: "",
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
                    value = password2,
                    onValueChange = onPassword2Changed,
                    isError = password2ErrorMessage != null,
                    errorMessage = password2ErrorMessage ?: "",
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
                    value = email,
                    onValueChange = onEmailChanged,
                    isError = emailErrorMessage != null,
                    errorMessage = emailErrorMessage ?: "",
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
                    value = nickname,
                    onValueChange = onNicknameChanged,
                    isError = nicknameErrorMessage != null,
                    errorMessage = nicknameErrorMessage ?: "",
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