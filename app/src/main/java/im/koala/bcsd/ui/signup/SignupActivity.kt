package im.koala.bcsd.ui.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.button.KoalaButton
import im.koala.bcsd.ui.indicator.KoalaDotIndicator
import im.koala.bcsd.ui.signup.compose.SignupCompletedDialog
import im.koala.bcsd.ui.signup.compose.SignupInputUserInfo
import im.koala.bcsd.ui.signup.compose.SignupPermissionScreen
import im.koala.bcsd.ui.signup.compose.SignupTermScreen
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.util.PasswordChecker
import im.koala.bcsd.util.compose.Keyboard
import im.koala.bcsd.util.compose.keyboardAsState
import kotlinx.coroutines.launch

const val STEP_TERMS = 0
const val STEP_PERMISSION = 1
const val STEP_INPUT_USER_INFO = 2

const val STEP_COUNT = 3

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class SignupActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SignupContent()
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SignupContent(signupViewModel: SignupViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as SignupActivity
    val keyboardOpened by keyboardAsState()

    val step = rememberSaveable { mutableStateOf(STEP_TERMS) }

    val isCheckedTermsPrivacy = rememberSaveable { mutableStateOf(false) }
    val isCheckedTermsKoala = rememberSaveable { mutableStateOf(false) }

    val id = signupViewModel.id.observeAsState("")
    val password = signupViewModel.password.observeAsState("")
    val password2 = signupViewModel.password2.observeAsState("")
    val email = signupViewModel.email.observeAsState("")
    val nickname = signupViewModel.nickname.observeAsState("")
    val idErrorCode = signupViewModel.idErrorCode.observeAsState(ID_OK)
    val passwordErrorCode =
        signupViewModel.passwordErrorCode.observeAsState(PasswordChecker.PASSWORD_OK)
    val passwordMatch = signupViewModel.passwordMatch.observeAsState(true)
    val emailErrorCode = signupViewModel.emailErrorCode.observeAsState(EMAIL_OK)
    val nicknameErrorCode = signupViewModel.nicknameErrorCode.observeAsState(NICKNAME_OK)
    val signupCompleted = signupViewModel.signupCompleted.observeAsState(false)

    val canSignUp = idErrorCode.value == ID_OK &&
        passwordErrorCode.value == PasswordChecker.PASSWORD_OK &&
        passwordMatch.value &&
        emailErrorCode.value == EMAIL_OK &&
        nicknameErrorCode.value == NICKNAME_OK &&
        id.value.isNotEmpty() &&
        password.value.isNotEmpty() &&
        password2.value.isNotEmpty() &&
        email.value.isNotEmpty() &&
        nickname.value.isNotEmpty()

    val nextButtonEnabled = when (step.value) {
        STEP_TERMS -> isCheckedTermsPrivacy.value && isCheckedTermsKoala.value
        STEP_PERMISSION -> true
        STEP_INPUT_USER_INFO -> canSignUp
        else -> false
    }

    val nextButtonText =
        if (step.value == STEP_INPUT_USER_INFO) {
            stringResource(R.string.signup_finish)
        } else {
            stringResource(R.string.signup_next)
        }

    val onBack = {
        if (step.value == STEP_TERMS) activity.finish()
        else step.value--
        Unit
    }

    BackHandler(onBack = onBack)

    KoalaTheme {
        if (signupCompleted.value) {
            SignupCompletedDialog {
                // TODO : Login screen gogo
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    KoalaTextAppBar(
                        title = stringResource(R.string.app_bar_title_signup),
                        showBackButton = true,
                        onBackClick = onBack
                    ) {}
                },
                bottomBar = {
                    if (keyboardOpened == Keyboard.Closed) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            KoalaDotIndicator(
                                modifier = Modifier.padding(16.dp),
                                dotCount = STEP_COUNT, dotPosition = step.value
                            )

                            KoalaButton(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                                    .fillMaxWidth()
                                    .height(48.dp),
                                onClick = {
                                    if (step.value == STEP_INPUT_USER_INFO) {
                                        coroutineScope.launch {
                                            signupViewModel.signUp()
                                        }
                                    } else {
                                        step.value++
                                    }
                                },
                                enabled = nextButtonEnabled
                            ) {
                                Text(text = nextButtonText)
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    AnimatedContent(
                        targetState = step.value,
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInHorizontally({ width -> width / 2 }) + fadeIn() with
                                    slideOutHorizontally({ width -> -width / 2 }) + fadeOut()
                            } else {
                                slideInHorizontally({ width -> -width / 2 }) + fadeIn() with
                                    slideOutHorizontally({ width -> width / 2 }) + fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        }
                    ) { step ->
                        when (step) {
                            STEP_TERMS -> SignupTermScreen(
                                isCheckedTermsPrivacy,
                                isCheckedTermsKoala
                            )
                            STEP_PERMISSION -> SignupPermissionScreen()
                            STEP_INPUT_USER_INFO -> SignupInputUserInfo(
                                id = id.value,
                                password = password.value,
                                password2 = password2.value,
                                email = email.value,
                                nickname = nickname.value,
                                idErrorMessage = when (idErrorCode.value) {
                                    ID_IS_DUPLICATED -> stringResource(R.string.signup_input_error_id_duplicated)
                                    else -> null
                                },
                                passwordErrorMessage = PasswordChecker.PasswordErrorString(
                                    passwordErrorCode = passwordErrorCode.value
                                ),
                                password2ErrorMessage = if (!passwordMatch.value) {
                                    stringResource(id = R.string.signup_input_error_password_not_match)
                                } else {
                                    null
                                },
                                emailErrorMessage = when (emailErrorCode.value) {
                                    EMAIL_IS_NOT_EMAIL_FORMAT -> stringResource(R.string.signup_input_error_email_format_not_match)
                                    else -> null
                                },
                                nicknameErrorMessage = when (nicknameErrorCode.value) {
                                    NICKNAME_IS_DUPLICATED -> stringResource(R.string.signup_input_error_nickname_duplicated)
                                    else -> null
                                },
                                onIdChanged = {
                                    signupViewModel.setId(it)
                                },
                                onPasswordChanged = {
                                    signupViewModel.setPassword(it)
                                },
                                onPassword2Changed = {
                                    signupViewModel.setPassword2(it)
                                },
                                onEmailChanged = {
                                    signupViewModel.setEmail(it)
                                },
                                onNicknameChanged = {
                                    signupViewModel.setNickname(it)
                                },
                                onFocusChanged = {}
                            )
                        }
                    }
                }
            }
        }
    }
}