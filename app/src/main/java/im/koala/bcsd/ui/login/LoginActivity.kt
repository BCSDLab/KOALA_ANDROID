package im.koala.bcsd.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import im.koala.bcsd.R
import im.koala.bcsd.ui.findid.FindIdActivity
import im.koala.bcsd.ui.findpassword.FindPasswordActivity
import im.koala.bcsd.ui.join.JoinActivity
import im.koala.bcsd.ui.theme.*
import kotlinx.coroutines.NonDisposableHandle.parent

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoalaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    constraintLayoutContent()
                }
            }
        }
    }
}

@Composable
fun constraintLayoutContent() {
    val context = LocalContext.current
    var loginTabState = remember { mutableStateOf(true) } //true - 일반로그인, false, SNS로그인
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logoImageView, loginRowLayout, normalLoginConstraintLyaout, snsLoginConstraintLyaout) = createRefs()

        drawImageView(modifier = Modifier.constrainAs(logoImageView) {
            top.linkTo(parent.top, margin = 48.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, drawableId = R.drawable.ic_koala_logo)
        Row(modifier = Modifier
            .constrainAs(loginRowLayout) {
                top.linkTo(logoImageView.bottom, margin = 40.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                width = Dimension.fillToConstraints
            }
            .size(0.dp, 71.dp)){
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                TextButton(
                    onClick = { loginTabState.value = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.normal_login),
                        color = Black,
                        style = MaterialTheme.typography.h2,
                    )
                }
                if(loginTabState.value) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = Black
                    )
                } else {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = Gray
                    )
                }

            }
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)) {
                TextButton(
                    onClick = { loginTabState.value = false },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sns_login),
                        color = Black,
                        style = MaterialTheme.typography.h2,
                    )
                }
                if(!loginTabState.value) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = Black
                    )
                } else {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = Gray
                    )
                }
            }
        }
        if(loginTabState.value) {
            ConstraintLayout(modifier = Modifier.constrainAs(normalLoginConstraintLyaout) {
                top.linkTo(loginRowLayout.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }) {
                val (idEditText, pwEditText, autoLoginSwitch, autoLoginText, loginButton, rowLayout, snsLoginText, kakaoLoginButton, naverLoginButton, googleLoginButton, nonMemberLoginButton) = createRefs()
                val idTextState = remember { mutableStateOf(TextFieldValue()) }
                val isAutoLoginState = remember { mutableStateOf(false) }


                OutlinedTextField(
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
                            color = Gray,
                            style = MaterialTheme.typography.body1,
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, // ** Go to next **
                        keyboardType = KeyboardType.Email
                    )
                )
                OutlinedTextField(
                    value = idTextState.value,
                    onValueChange = { idTextState.value = it },
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
                            color = Gray,
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
                        checkedThumbColor = Yellow,
                        uncheckedThumbColor = Yellow,
                        uncheckedTrackColor = GrayDisabled
                    )
                )
                Text(
                    text = stringResource(id = R.string.login_state_persistance),
                    modifier = Modifier.constrainAs(autoLoginText) {
                        top.linkTo(pwEditText.bottom, margin = 10.dp)
                        start.linkTo(autoLoginSwitch.end, margin = 14.dp)
                    },
                    color = Gray,
                    style = MaterialTheme.typography.body2
                )
                Button(
                    onClick = { /*TODO*/ },
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
                        backgroundColor = Color.Black,
                    )

                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = White,
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
                            Intent(context, JoinActivity::class.java).run {
                                context.startActivity(this)
                            }
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
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    modifier = Modifier.constrainAs(snsLoginText) {
                        top.linkTo(rowLayout.bottom, 72.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                ){
                    Text(
                        text = stringResource(id = R.string.use_non_member),
                        color = GrayNormal,
                        style = MaterialTheme.typography.button
                    )
                }

            }
        } else {
            ConstraintLayout(modifier = Modifier.constrainAs(normalLoginConstraintLyaout) {
                top.linkTo(loginRowLayout.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }){
                val (googleButton,googleIcon, naverButton, naverIcon, kakaoButton, kakaoIcon) = createRefs()
                /*구글버튼*/
                snsLoginButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .constrainAs(googleButton) {
                            top.linkTo(parent.top, margin = 37.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(parent.end, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        }
                        .border(width = 1.dp, color = Gray, shape = RectangleShape),
                    backgroundColor = White,
                    textColor = Black,
                    text = stringResource(id = R.string.google_login)) {

                }

                drawImageView(
                    modifier = Modifier.size(18.dp, 18.dp).constrainAs(googleIcon) {
                        top.linkTo(googleButton.top)
                        bottom.linkTo(googleButton.bottom)
                        start.linkTo(googleButton.start, margin = 13.dp)
                    },
                    drawableId = R.drawable.ic_google_logo
                )
                /*네이버 버튼*/
                snsLoginButton(
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
                    text = stringResource(id = R.string.naver_login)) {

                }

                drawImageView(
                    modifier = Modifier.size(18.dp, 18.dp).constrainAs(naverIcon) {
                        top.linkTo(naverButton.top)
                        bottom.linkTo(naverButton.bottom)
                        start.linkTo(naverButton.start, margin = 13.dp)
                    },
                    drawableId = R.drawable.ic_naver_logo
                )

                /*카카오 버튼*/
                snsLoginButton(
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
                    text = stringResource(id = R.string.kakao_login)) {

                }

                drawImageView(
                    modifier = Modifier.size(18.dp, 18.dp).constrainAs(kakaoIcon) {
                        top.linkTo(kakaoButton.top)
                        bottom.linkTo(kakaoButton.bottom)
                        start.linkTo(kakaoButton.start, margin = 13.dp)
                    },
                    drawableId = R.drawable.ic_kakao_logo
                )
            }
        }


    }
}
@Composable
fun snsLoginButton(modifier: Modifier, backgroundColor: Color, textColor: Color, text: String, onClick: () -> Unit) {
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
fun drawImageView(modifier: Modifier, drawableId: Int) {
    Image(
        painter = painterResource(id = drawableId),
        contentDescription = null,
        modifier = modifier

    )
}
