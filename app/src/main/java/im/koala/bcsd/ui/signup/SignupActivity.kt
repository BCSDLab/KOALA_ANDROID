package im.koala.bcsd.ui.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.button.KoalaButton
import im.koala.bcsd.ui.indicator.KoalaDotIndicator
import im.koala.bcsd.ui.signup.compose.SignupPermissionScreen
import im.koala.bcsd.ui.signup.compose.SignupTermScreen
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.util.compose.Keyboard
import im.koala.bcsd.util.compose.keyboardAsState

const val STEP_TERMS = "STEP_TERMS"
const val STEP_PERMISSION = "STEP_PERMISSION"
const val STEP_INPUT_USER_INFO = "STEP_INPUT_USER_INFO"

const val DOT_COUNT = 3

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
    val navController = rememberNavController().apply {
        enableOnBackPressed(true)
    }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as SignupActivity
    val keyboardOpened by keyboardAsState()

    val nextButtonText = rememberSaveable { mutableStateOf("") }
    val nextButtonEnabled = rememberSaveable { mutableStateOf(false) }

    val dotPosition = rememberSaveable { mutableStateOf(0) }

    KoalaTheme {
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
                        onBackClick = { navController.navigateUp() }
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
                                dotCount = DOT_COUNT,
                                dotPosition = dotPosition.value
                            )

                            KoalaButton(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, bottom = 40.dp)
                                    .fillMaxWidth()
                                    .height(48.dp),
                                onClick = {
                                },
                                enabled = nextButtonEnabled.value
                            ) {
                                Text(text = nextButtonText.value)
                            }
                        }
                    }
                }
            ) { innerPadding ->
                NavHost(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    navController = navController,
                    startDestination = STEP_TERMS
                ) {
                    composable(STEP_TERMS) {
                        dotPosition.value = 0
                        nextButtonText.value = stringResource(id = R.string.signup_next)
                        SignupTermScreen {
                            nextButtonEnabled.value = it
                        }
                    }

                    composable(STEP_PERMISSION) {
                        dotPosition.value = 1
                        nextButtonText.value = stringResource(id = R.string.signup_next)
                        nextButtonEnabled.value = true
                        SignupPermissionScreen()
                    }
                }
                /*Box(
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
                            STEP_TERMS -> SignupTermScreen {
                                nextButtonEnabled.value = it
                            }
                            STEP_PERMISSION -> {
                                SignupPermissionScreen()
                                nextButtonEnabled.value = true
                            }
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
                }*/
            }
        }
    }
}