package im.koala.bcsd.ui.button

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.KoalaTheme

object KoalaButtonColors {

    val backgroundColor: Color
        @Composable get() = if (isSystemInDarkTheme()) Black else Black

    val contentColor: Color
        @Composable get() = if (isSystemInDarkTheme()) White else White

    val disabledBackgroundColor: Color
        @Composable get() = if (isSystemInDarkTheme()) GrayDisabled else GrayDisabled

    val disabledContentColor: Color
        @Composable get() = if (isSystemInDarkTheme()) White else White
}

@Composable
fun KoalaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        border = null,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = KoalaButtonColors.backgroundColor,
            contentColor = KoalaButtonColors.contentColor,
            disabledBackgroundColor = KoalaButtonColors.disabledBackgroundColor,
            disabledContentColor = KoalaButtonColors.disabledContentColor
        ),
        content = content
    )
}

@Preview(name = "Basic Koala Button(enabled)")
@Composable
fun KoalaButtonPreview() {
    KoalaTheme {
        KoalaButton(
            onClick = {},
            modifier = Modifier
                .width(320.dp)
                .height(48.dp),
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
            modifier = Modifier
                .width(320.dp)
                .height(48.dp),
            enabled = false
        ) {
            Text(text = "Disabled")
        }
    }
}