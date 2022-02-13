package im.koala.bcsd.ui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.GrayBorder
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
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = KoalaButtonColors.backgroundColor,
            contentColor = KoalaButtonColors.contentColor,
            disabledBackgroundColor = KoalaButtonColors.disabledBackgroundColor,
            disabledContentColor = KoalaButtonColors.disabledContentColor
        ),
        content = content,
        contentPadding = PaddingValues(0.dp)
    )
}

@Composable
fun KoalaBackgroundButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, GrayBorder),
        elevation = ButtonDefaults.elevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            disabledBackgroundColor = MaterialTheme.colors.background,
            disabledContentColor = GrayBorder
        ),
        content = content,
        contentPadding = PaddingValues(0.dp)
    )
}

@Preview(name = "Basic Koala Button(enabled)")
@Composable
private fun KoalaButtonPreview() {
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
private fun KoalaButtonDisabledPreview() {
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

@Preview(name = "Basic Koala Background Button(enabled)")
@Composable
private fun KoalaBackgroundButtonPreview() {
    KoalaTheme {
        Surface {
            KoalaBackgroundButton(
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .height(34.dp),
                enabled = true
            ) {
                Text(text = "Enabled")
            }
        }
    }
}

@Preview(name = "Basic Koala Background Button(disabled)")
@Composable
private fun KoalaBackgroundButtonDisabledPreview() {
    KoalaTheme {
        Surface {
            KoalaBackgroundButton(
                onClick = {},
                modifier = Modifier
                    .padding(16.dp)
                    .height(34.dp),
                enabled = false
            ) {
                Text(text = "Disabled")
            }
        }
    }
}