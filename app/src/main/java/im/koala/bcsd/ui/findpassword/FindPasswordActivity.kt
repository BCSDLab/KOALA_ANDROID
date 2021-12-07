package im.koala.bcsd.ui.findpassword

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
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
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

class FindPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoalaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    FindPasswordScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    KoalaTheme() {
        NewPassword(modifier = Modifier)
    }
}

@Composable
fun FindPasswordScreen() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (appBar, findPasswordLayout) = createRefs()

        KoalaTextAppBar(
            title = stringResource(id = R.string.find_password_app_bar),
            modifier = Modifier.constrainAs(appBar) {
                top.linkTo(parent.top, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }) {
        }
        FindPassword(
            modifier = Modifier.constrainAs(findPasswordLayout) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            })
    }
}

@Composable
fun FindPassword(
    modifier: Modifier
) {
    ConstraintLayout(modifier = modifier) {
        val (idEditText, emailEditText, verifyNumberEditText, sendVerifyNumberButton, verifyDoneButton) = createRefs()
        val idTextState = remember { mutableStateOf(TextFieldValue()) }
        val emailTextState = remember { mutableStateOf(TextFieldValue()) }
        val verifyNumberState = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = idTextState.value,
            onValueChange = { idTextState.value = it },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(idEditText) {
                    top.linkTo(parent.top, margin = 88.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(width = 1.dp, GrayBorder, shape = RectangleShape),

            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.find_password_id),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next, // ** Go to next **
                keyboardType = KeyboardType.Email
            )
        )
        OutlinedTextField(
            value = emailTextState.value,
            onValueChange = { emailTextState.value = it },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(emailEditText) {
                    top.linkTo(idEditText.bottom, margin = 24.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(width = 1.dp, GrayBorder, shape = RectangleShape),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.find_password_email),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                )
            },
        )
        OutlinedTextField(
            value = verifyNumberState.value,
            onValueChange = { verifyNumberState.value = it },
            modifier = Modifier
                .size(197.dp, 48.dp)
                .constrainAs(verifyNumberEditText) {
                    top.linkTo(emailEditText.bottom, margin = 24.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                }
                .border(width = 1.dp, GrayBorder, shape = RectangleShape),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.find_password_put_verify_number),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                )
            },
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(115.dp, 48.dp)
                .constrainAs(sendVerifyNumberButton) {
                    top.linkTo(emailEditText.bottom, 24.dp)
                    start.linkTo(verifyNumberEditText.end, 16.dp)
                }
                .background(color = Black, shape = RectangleShape),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
            )

        ) {
            Text(
                text = stringResource(id = R.string.find_password_send_verify_number),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.button
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(verifyDoneButton) {
                    bottom.linkTo(parent.bottom, 40.dp)
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
                text = stringResource(id = R.string.find_password_verify_done),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun NewPassword(
    modifier: Modifier
) {
    ConstraintLayout(modifier = modifier.fillMaxSize()) {
        val (changePasswordText, changePasswordSubText, newPasswordEditText, checkPasswordEditText, doneButton) = createRefs()
        val newPasswordEditTextState = remember { mutableStateOf(TextFieldValue()) }
        val checkPasswordEditTextState = remember { mutableStateOf(TextFieldValue()) }
        Text(
            text = stringResource(id = R.string.find_password_new_password),
            modifier = Modifier.constrainAs(changePasswordText) {
                top.linkTo(parent.top, margin = 88.dp)
                start.linkTo(parent.start, margin = 16.dp)
            },
            color = MaterialTheme.colors.secondary,
            fontSize = 16.sp,
            style = MaterialTheme.typography.body2
        )
        Text(
            text = stringResource(id = R.string.find_password_new_password_sub),
            modifier = Modifier.constrainAs(changePasswordSubText) {
                top.linkTo(changePasswordText.bottom, margin = 8.dp)
                start.linkTo(parent.start, margin = 16.dp)
            },
            color = MaterialTheme.colors.background,
            fontSize = 12.sp,
            style = MaterialTheme.typography.body2
        )
        OutlinedTextField(
            value = newPasswordEditTextState.value,
            onValueChange = { newPasswordEditTextState.value = it },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(newPasswordEditText) {
                    top.linkTo(changePasswordSubText.bottom, margin = 24.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(width = 1.dp, GrayBorder, shape = RectangleShape),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.find_password_new_password_form),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                )
            }
        )

        OutlinedTextField(
            value = checkPasswordEditTextState.value,
            onValueChange = { checkPasswordEditTextState.value = it },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(checkPasswordEditText) {
                    top.linkTo(newPasswordEditText.bottom, margin = 24.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
                .border(width = 1.dp, GrayBorder, shape = RectangleShape),
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.find_password_check_new_password),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1,
                )
            },
        )

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .size(0.dp, 48.dp)
                .constrainAs(doneButton) {
                    bottom.linkTo(parent.bottom, 40.dp)
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
                text = stringResource(id = R.string.find_password_done),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.button
            )
        }
    }
}
