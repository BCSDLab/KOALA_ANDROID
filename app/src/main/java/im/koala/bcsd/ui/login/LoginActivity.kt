package im.koala.bcsd.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint
import im.koala.bcsd.R
import im.koala.bcsd.ui.findid.FindIdActivity
import im.koala.bcsd.ui.findpassword.FindPasswordActivity
import im.koala.bcsd.ui.main.MainActivity
import im.koala.bcsd.ui.signup.SignUpContract
import im.koala.bcsd.ui.textfield.KoalaTextField
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.GrayNormal
import im.koala.bcsd.ui.theme.Green
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.ui.theme.White
import im.koala.bcsd.ui.theme.Yellow2
import im.koala.domain.constants.GOOGLE
import im.koala.domain.constants.KAKAO
import im.koala.domain.constants.NAVER
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val googleSignInResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            viewModel.emitSnsToken(account.idToken!!)
        } catch (e: Exception) {
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        initObserver()
        setContent {
            KoalaTheme {
                Surface(color = MaterialTheme.colors.background) {
                    LoginScreen(viewModel = viewModel)
                }
            }
        }
    }
    private fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiEvent.collect { uiEvent ->
                when (uiEvent) {
                    LoginViewUIEvent.GoToMainActivity -> {
                        goToMainActivity()
                        finish()
                    }
                    LoginViewUIEvent.ProceedGoogleLogin -> { proceedGoogleLogin(this@LoginActivity) }
                    LoginViewUIEvent.ProceedNaverLogin -> { proceedNaverLogin() }
                    LoginViewUIEvent.ProceedKakaoLogin -> { proceedKakaoLogin() }
                    is LoginViewUIEvent.ShowErrorMessage -> {
                        callToastMessage(context = this@LoginActivity, message = uiEvent.message)
                    }
                }
            }
        }
    }
    fun callToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
    private fun proceedNaverLogin() {
        NaverIdLoginSDK.authenticate(this@LoginActivity, viewModel.naverLoginCallback)
    }
    private fun proceedKakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = viewModel.kakaoCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = viewModel.kakaoCallback)
        }
    }
    private fun proceedGoogleLogin(context: Context) {
        var signInIntent = getGoogleClient(context).signInIntent
        googleSignInResult.launch(signInIntent)
    }
    private fun getGoogleClient(context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).run {
            startActivity(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val isNormalLoginState = remember { mutableStateOf(true) }
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logoImageView, loginRowLayout, normalLoginConstraintLyaout) = createRefs()

        DrawImageView(
            modifier = Modifier.constrainAs(logoImageView) {
                top.linkTo(parent.top, margin = 48.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            drawableId = R.drawable.ic_koala_logo
        )
        LoginTypeTabScreen(
            modifier = Modifier
                .constrainAs(loginRowLayout) {
                    top.linkTo(logoImageView.bottom, margin = 40.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .size(0.dp, 71.dp),
            isNormalLoginState
        )
        if (isNormalLoginState.value) {
            NormalScreen(
                modifier = Modifier.constrainAs(normalLoginConstraintLyaout) {
                    top.linkTo(loginRowLayout.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
                context = context,
                viewModel = viewModel
            )
        } else {
            SnsLoginScreen(
                context = context,
                modifier = Modifier.constrainAs(normalLoginConstraintLyaout) {
                    top.linkTo(loginRowLayout.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun LoginTypeTabScreen(
    modifier: Modifier,
    isNormalLoginState: MutableState<Boolean>
) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            TextButton(
                onClick = { isNormalLoginState.value = true },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.normal_login),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h2,
                )
            }
            if (isNormalLoginState.value) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = MaterialTheme.colors.secondary
                )
            } else {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            TextButton(
                onClick = { isNormalLoginState.value = false },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sns_login),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h2,
                )
            }
            if (!isNormalLoginState.value) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = MaterialTheme.colors.secondary
                )
            } else {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = MaterialTheme.colors.onBackground
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun NormalScreen(
    modifier: Modifier,
    context: Context,
    viewModel: LoginViewModel
) {
    val signUpContract = rememberLauncherForActivityResult(contract = SignUpContract()) {
        // 회원가입 성공하면 회원가입 때 사용한 id 반환, 아니면 null
    }
    ConstraintLayout(modifier = modifier) {
        val (idEditText, pwEditText, autoLoginSwitch, autoLoginText, loginButton, rowLayout, snsLoginText) = createRefs()
        val idTextState = remember { mutableStateOf("") }
        val pwTextState = remember { mutableStateOf("") }
        val isAutoLoginState = remember { mutableStateOf(false) }
        KoalaTextField(
            value = idTextState.value,
            onValueChange = { idTextState.value = it },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(idEditText) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(width = 1.dp, GrayBorder, shape = RectangleShape),

            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.login_id),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, // ** Go to next **
                keyboardType = KeyboardType.Email
            )
        )
        KoalaTextField(
            value = pwTextState.value,
            onValueChange = { pwTextState.value = it },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(pwEditText) {
                    top.linkTo(idEditText.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(width = 1.dp, GrayBorder, shape = RectangleShape),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.login_pw),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                )
            },
        )
        Switch(
            checked = isAutoLoginState.value,
            onCheckedChange = { isAutoLoginState.value = it },
            modifier = Modifier
                .size(22.dp, 12.dp)
                .constrainAs(autoLoginSwitch) {
                    top.linkTo(pwEditText.bottom, margin = 11.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.onError,
                uncheckedThumbColor = MaterialTheme.colors.onError,
                uncheckedTrackColor = GrayDisabled
            )
        )
        Text(
            text = stringResource(id = R.string.login_state_persistance),
            modifier = Modifier.constrainAs(autoLoginText) {
                top.linkTo(pwEditText.bottom, margin = 10.dp)
                start.linkTo(autoLoginSwitch.end, margin = 14.dp)
            },
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.body2
        )
        Button(
            onClick = {
                viewModel.login(
                    autoLogin = isAutoLoginState.value,
                    id = idTextState.value,
                    password = pwTextState.value
                )
            },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(loginButton) {
                    top.linkTo(autoLoginSwitch.bottom, 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .background(color = Black, shape = RectangleShape),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
            )

        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.button
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(rowLayout) {
                    top.linkTo(loginButton.bottom, 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .wrapContentSize(align = Alignment.Center)
        ) {
            TextButton(
                onClick = {
                    Intent(context, FindIdActivity::class.java).run {
                        context.startActivity(this)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.find_id),
                    color = GrayNormal,
                    style = MaterialTheme.typography.body2
                )
            }
            Divider(
                color = GrayNormal,
                modifier = Modifier
                    .size(1.dp, 14.dp)
            )
            TextButton(
                onClick = {
                    signUpContract.launch(null)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.join_koala),
                    color = GrayNormal,
                    style = MaterialTheme.typography.body2
                )
            }
            Divider(
                color = GrayNormal,
                modifier = Modifier
                    .size(1.dp, 14.dp)
            )
            TextButton(
                onClick = {
                    Intent(context, FindPasswordActivity::class.java).run {
                        context.startActivity(this)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.find_pw),
                    color = GrayNormal,
                    style = MaterialTheme.typography.body2
                )
            }
        }
        TextButton(
            onClick = {
                viewModel.loginNonMember(autoLogin = isAutoLoginState.value)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            modifier = Modifier.constrainAs(snsLoginText) {
                top.linkTo(rowLayout.bottom, 72.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
        ) {
            Text(
                text = stringResource(id = R.string.use_non_member),
                color = GrayNormal,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun SnsLoginScreen(
    context: Context,
    modifier: Modifier,
    viewModel: LoginViewModel
) {
    ConstraintLayout(modifier = modifier) {
        val (googleButton, googleIcon, naverButton, naverIcon, kakaoButton, kakaoIcon) = createRefs()
        /*구글버튼*/
        SnsLoginButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .constrainAs(googleButton) {
                    top.linkTo(parent.top, margin = 37.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = RectangleShape
                ),
            backgroundColor = White,
            textColor = Black,
            text = stringResource(id = R.string.google_login),
            onClick = {
                viewModel.onClickSnsLoginButton(GOOGLE)
                viewModel.snsType = GOOGLE
            }
        )

        DrawImageView(
            modifier = Modifier
                .size(18.dp, 18.dp)
                .constrainAs(googleIcon) {
                    top.linkTo(googleButton.top)
                    bottom.linkTo(googleButton.bottom)
                    start.linkTo(googleButton.start, margin = 13.dp)
                },
            drawableId = R.drawable.ic_google_logo
        )
        /*네이버 버튼*/
        SnsLoginButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .constrainAs(naverButton) {
                    top.linkTo(googleButton.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
            backgroundColor = Green,
            textColor = White,
            text = stringResource(id = R.string.naver_login),
            onClick = {
                viewModel.snsType = NAVER
                viewModel.onClickSnsLoginButton(NAVER)
            }
        )
        DrawImageView(
            modifier = Modifier
                .size(18.dp, 18.dp)
                .constrainAs(naverIcon) {
                    top.linkTo(naverButton.top)
                    bottom.linkTo(naverButton.bottom)
                    start.linkTo(naverButton.start, margin = 13.dp)
                },
            drawableId = R.drawable.ic_naver_logo
        )

        /*카카오 버튼*/
        SnsLoginButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .constrainAs(kakaoButton) {
                    top.linkTo(naverButton.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
            backgroundColor = Yellow2,
            textColor = Black,
            text = stringResource(id = R.string.kakao_login),
            onClick = {
                viewModel.onClickSnsLoginButton(KAKAO)
                viewModel.snsType = KAKAO
            }
        )
        DrawImageView(
            modifier = Modifier
                .size(18.dp, 18.dp)
                .constrainAs(kakaoIcon) {
                    top.linkTo(kakaoButton.top)
                    bottom.linkTo(kakaoButton.bottom)
                    start.linkTo(kakaoButton.start, margin = 13.dp)
                },
            drawableId = R.drawable.ic_kakao_logo
        )
    }
}

@Composable
fun CallToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun SnsLoginButton(
    modifier: Modifier,
    backgroundColor: Color,
    textColor: Color,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        )
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun DrawImageView(modifier: Modifier, drawableId: Int) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = null,
        modifier = modifier
    )
}