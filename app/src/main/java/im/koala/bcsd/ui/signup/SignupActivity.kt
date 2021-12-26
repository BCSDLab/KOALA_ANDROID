package im.koala.bcsd.ui.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.button.KoalaButton
import im.koala.bcsd.ui.indicator.KoalaDotIndicator
import im.koala.bcsd.ui.signup.compose.SignupCompletedDialog
import im.koala.bcsd.ui.signup.compose.SignupInputUserInfoScreen
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
@AndroidEntryPoint
class SignupActivity : ComponentActivity() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SignupContent(signUpViewModel)
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun SignupContent(signUpViewModel: SignUpViewModel) {
    val navController = rememberNavController().apply {
        enableOnBackPressed(true)
    }
    val activity = LocalContext.current as Activity
    val keyboardOpened by keyboardAsState()

    val nextButtonText = rememberSaveable { mutableStateOf("") }
    val nextButtonEnabled = rememberSaveable { mutableStateOf(false) }

    val dotPosition = rememberSaveable { mutableStateOf(0) }

    KoalaTheme {
        if (signUpViewModel.signupCompleted) {
            SignupCompletedDialog {
                activity.setResult(
                    Activity.RESULT_OK,
                    Intent().apply {
                        putExtra(SignUpContract.LOGIN_ID, signUpViewModel.signUpValueUiState.id)
                    }
                )
                activity.finish()
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
                        onBackClick = {
                            if (!navController.navigateUp()) activity.finish()
                        }
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
                                    when (navController.currentBackStackEntry?.destination?.route) {
                                        STEP_TERMS -> navController.navigate(STEP_PERMISSION)
                                        STEP_PERMISSION -> navController.navigate(
                                            STEP_INPUT_USER_INFO
                                        )
                                        STEP_INPUT_USER_INFO -> signUpViewModel.signUp()
                                    }
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

                    composable(STEP_INPUT_USER_INFO) {
                        dotPosition.value = 2
                        nextButtonText.value = stringResource(id = R.string.signup_finish)
                        SignupInputUserInfoScreen(
                            signUpInputUiState = signUpViewModel.signUpValueUiState,
                            onIdChanged = signUpViewModel::setId,
                            onPasswordChanged = signUpViewModel::setPassword,
                            onPasswordConfirmChanged = signUpViewModel::setPasswordConfirm,
                            onEmailChanged = signUpViewModel::setEmail,
                            onNicknameChanged = signUpViewModel::setNickname
                        )
                    }
                }
            }
        }
    }
}