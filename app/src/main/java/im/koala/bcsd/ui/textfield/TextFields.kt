package im.koala.bcsd.ui.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun KoalaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    singleLine: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (RowScope.() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.subtitle2,
        maxLines = maxLines,
        label = label,
        placeholder = {
            if (placeholder != null) {
                ProvideTextStyle(value = MaterialTheme.typography.subtitle2) {
                    placeholder()
                }
            }
        },
        leadingIcon = leadingIcon,
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isError) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_exclamation),
                        contentDescription = "",
                        modifier = Modifier.padding(8.dp)
                    )
                }
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.secondary) {
                    if (trailingIcon != null) {
                        trailingIcon()
                    }
                }
            }
        },
        isError = isError,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = GrayBorder,
            cursorColor = MaterialTheme.colors.onBackground,
            textColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = MaterialTheme.colors.onBackground,
            focusedLabelColor = MaterialTheme.colors.secondary,
            errorBorderColor = MaterialTheme.colors.onError,
            errorLabelColor = MaterialTheme.colors.onError,
            placeholderColor = MaterialTheme.colors.onBackground,
            errorLeadingIconColor = MaterialTheme.colors.onError,
            errorTrailingIconColor = MaterialTheme.colors.onError,
            leadingIconColor = MaterialTheme.colors.onBackground,
            trailingIconColor = MaterialTheme.colors.onBackground,
        ),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource
    )
}

@Composable
fun KoalaPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    showPassword: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions()
) {
    val showPassword = rememberSaveable { mutableStateOf(showPassword) }

    KoalaTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = {
            Icon(
                painter = painterResource(id = if (showPassword.value) R.drawable.ic_eye else R.drawable.ic_eye_off),
                contentDescription = "",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        showPassword.value = !showPassword.value
                    }
            )
            if (trailingIcon != null) {
                trailingIcon()
            }
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            autoCorrect = false,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        interactionSource = interactionSource,
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation(
                mask = '⬤'
            )
        }
    )
}

@Preview(name = "Koala Text Field")
@Composable
private fun KoalaTextFieldPreview() {
    KoalaTheme {
        val text = remember { mutableStateOf("") }

        KoalaTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            placeholder = { Text(text = "Text Label") },
        )
    }
}

@Preview(name = "Koala Text Field(Error)")
@Composable
private fun KoalaTextFieldErrorPreview() {
    KoalaTheme {
        val text = remember { mutableStateOf("TextField with error state") }

        KoalaTextField(
            value = text.value, onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            placeholder = { Text(text = "Text Label") },
            isError = true
        )
    }
}

@Preview(name = "Koala Text Field(Disabled)")
@Composable
private fun KoalaTextFieldDisabledPreview() {
    KoalaTheme {
        val text = remember { mutableStateOf("TextField with disabled state") }

        KoalaTextField(
            value = text.value, onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            placeholder = { Text(text = "Text Label") },
            enabled = false
        )
    }
}

@Preview(name = "Koala Password Text Field")
@Composable
private fun KoalaPasswordTextFieldPreview() {
    KoalaTheme {
        val text = remember { mutableStateOf("asdf1234") }

        KoalaPasswordTextField(
            value = text.value,
            onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            placeholder = { Text(text = "Text Label") },
        )
    }
}

@Preview(name = "Koala Password Text Field(error)")
@Composable
private fun KoalaPasswordTextFieldErrorPreview() {
    KoalaTheme {
        val text = remember { mutableStateOf("asdf1234") }

        KoalaPasswordTextField(
            value = text.value, onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            placeholder = { Text(text = "Text Label") },
            isError = true
        )
    }
}

@Preview(name = "Koala Password Text Field(password showing)")
@Composable
private fun KoalaPasswordShowingTextFieldPreview() {
    KoalaTheme {
        val text = remember { mutableStateOf("asdf1234") }

        KoalaPasswordTextField(
            value = text.value, onValueChange = {
                text.value = it
            },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp),
            placeholder = { Text(text = "Text Label") },
            showPassword = true
        )
    }
}