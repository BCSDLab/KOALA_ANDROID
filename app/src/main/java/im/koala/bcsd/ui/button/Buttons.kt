package im.koala.bcsd.ui.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.KoalaTheme


@Composable
fun KoalaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        ),
        shape = MaterialTheme.shapes.medium,
        border = null,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary,
            disabledBackgroundColor = GrayDisabled,
            disabledContentColor = MaterialTheme.colors.onSecondary
        ),
        contentPadding = contentPadding,
        content = content
    )
}

@Preview(name = "Basic Koala Button(enabled)")
@Composable
fun KoalaButtonPreview() {
    KoalaTheme {
        KoalaButton(
            onClick = {},
            modifier = Modifier.width(320.dp).height(48.dp),
            enabled = true
        ) {
            Text(text = "Enabled")
        }
    }
}

@Preview(name = "Basic Koala Button(disabled)")
@Composable
fun KoalaButtonDisabledPreview() {
    KoalaTheme {
        KoalaButton(
            onClick = {},
            modifier = Modifier.width(320.dp).height(48.dp),
            enabled = false
        ) {
            Text(text = "Disabled")
        }
    }
}