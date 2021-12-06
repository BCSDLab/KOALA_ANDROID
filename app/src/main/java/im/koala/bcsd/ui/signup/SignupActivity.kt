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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.button.KoalaButton
import im.koala.bcsd.ui.indicator.KoalaDotIndicator
import im.koala.bcsd.ui.signup.compose.SignupPermissionScreen
import im.koala.bcsd.ui.signup.compose.SignupTermScreen
import im.koala.bcsd.ui.theme.KoalaTheme

const val STEP_TERMS = 0
const val STEP_PERMISSION = 1
const val STEP_INPUT_USER_INFO = 2

const val STEP_COUNT = 3

@ExperimentalAnimationApi
class SignupActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SignupContent()
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun SignupContent() {
    val activity = LocalContext.current as SignupActivity
    val step = rememberSaveable { mutableStateOf(STEP_TERMS) }

    val isCheckedTermsPrivacy = rememberSaveable { mutableStateOf(false) }
    val isCheckedTermsKoala = rememberSaveable { mutableStateOf(false) }

    val nextButtonEnabled = when (step.value) {
        STEP_TERMS -> isCheckedTermsPrivacy.value && isCheckedTermsKoala.value
        STEP_PERMISSION -> true
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
                    }) { step ->
                        when(step) {
                            STEP_TERMS -> SignupTermScreen(isCheckedTermsPrivacy, isCheckedTermsKoala)
                            STEP_PERMISSION -> SignupPermissionScreen()
                        }
                    }
                }
            }
        }

    }
}