package im.koala.bcsd.ui.signup.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaCircularCheckBox
import im.koala.bcsd.ui.textfield.KoalaPasswordTextField
import im.koala.bcsd.ui.textfield.KoalaTextField
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun SignupSubtitle(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text1,
            modifier = Modifier,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )
        Text(
            text = text2,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun SignupTermBox(
    modifier: Modifier = Modifier,
    termsText: String
) {
    Box(
        modifier = modifier.border(
            width = 1.dp,
            color = GrayBorder
        )
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = termsText,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
fun SignupCheckBox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    content: @Composable (BoxScope.() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clickable(
                role = Role.Checkbox
            ) {
                onCheckedChange(!checked)
            }
            .padding(
                start = 16.dp,
                end = if (content == null) 16.dp else 4.dp,
                top = if (content == null) 12.dp else 0.dp,
                bottom = if (content == null) 12.dp else 0.dp
            ),
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            KoalaCircularCheckBox(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )

            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                text = text,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondary
            )
        }

        if (content != null) {
            content()
        }
    }

}

@Composable
fun SignupPermissionItem(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit),
    permissionName: String,
    permissionDescription: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        ) {
            icon()
        }

        Column(
            modifier = Modifier.padding(start = 24.dp)
        ) {
            Text(
                text = permissionName,
                modifier = Modifier,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondary
            )
            Text(
                text = permissionDescription,
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun SignupTextFieldWithErrorMessage(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    Box(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        KoalaTextField(
            modifier = Modifier.padding(bottom = 24.dp).height(48.dp).fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            singleLine = true,
            maxLines = 1,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            placeholder = {
                Text(text = hint)
            }
        )
        if(isError) {
            Text(
                text = errorMessage,
                modifier = Modifier
                    .padding(vertical = 4.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onError,
                fontSize = 11.sp
            )
        }

    }
}

@Composable
fun SignupPasswordTextFieldWithErrorMessage(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Box(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        KoalaPasswordTextField(
            modifier = Modifier.padding(bottom = 24.dp).height(48.dp).fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            imeAction = imeAction,
            placeholder = {
                Text(text = hint)
            },
            keyboardActions = keyboardActions
        )
        if (isError) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onError,
                fontSize = 11.sp
            )
        }
    }
}

@Preview("Signup Subtitle")
@Composable
private fun SignupSubtitlePreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            SignupSubtitle(
                text1 = "약관 동의",
                text2 = "원활한 사용을 위하여 필수 약관 동의가 필요합니다."
            )
        }
    }
}

@Preview("Signup Terms Box")
@Composable
private fun SignupTermBoxPreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            SignupTermBox(
                termsText = "제1조(목적)\n" +
                    "한강 서비스 이용약관은 bcsd lab에서 서비스를 제공함에 있어, 이용자간의 관리, 의무 및 책임 사항 등을 목적으로 합니다"
            )
        }
    }
}

@Preview("Signup Checkbox")
@Composable
private fun SignupCheckBoxPreview() {
    val checked = rememberSaveable { mutableStateOf(false) }

    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            SignupCheckBox(
                text = "약관 전체동의",
                checked = checked.value,
                onCheckedChange = {
                    checked.value = it
                })
        }
    }
}

@Preview("Signup Permission Item")
@Composable
private fun SignupPermissionItemPreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            SignupPermissionItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = ""
                    )
                },
                permissionName = "알림 (필수)",
                permissionDescription = "키워드 알림시 필수 이용"
            )
        }
    }
}

@Preview("Signup TextField with error message")
@Composable
private fun SignupTextFieldWithErrorMessagePreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            val text = rememberSaveable { mutableStateOf("") }

            SignupTextFieldWithErrorMessage(
                value = text.value,
                onValueChange = { text.value = it },
                isError = text.value != "asdf",
                errorMessage = "asdf 입력하면 사라짐",
                hint = "asdf"
            )
        }
    }
}

@Preview("Signup Password TextField with error message")
@Composable
private fun SignupPasswordTextFieldWithErrorMessagePreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            val text = rememberSaveable { mutableStateOf("") }

            SignupPasswordTextFieldWithErrorMessage(
                value = text.value,
                onValueChange = { text.value = it },
                isError = text.value != "asdf",
                errorMessage = "asdf 입력하면 사라짐",
                hint = "asdf"
            )
        }
    }
}