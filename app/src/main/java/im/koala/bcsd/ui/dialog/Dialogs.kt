package im.koala.bcsd.ui.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.window.DialogProperties

@Composable
fun KoalaAlertDialog(
    onDismissRequest: () -> Unit,
    buttons: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    text: @Composable (() -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.medium,
    properties: DialogProperties = DialogProperties()
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.body1,
                LocalContentColor provides MaterialTheme.colors.secondary
            ) {
                buttons()
            }
        },
        modifier = modifier,
        title = {
            if (title != null) {
                CompositionLocalProvider(
                    LocalTextStyle provides MaterialTheme.typography.body1,
                    LocalContentColor provides MaterialTheme.colors.secondary
                ) {
                    title()
                }
            }
        },
        text = {
               if(text != null) {
                   CompositionLocalProvider(
                       LocalTextStyle provides MaterialTheme.typography.body2,
                       LocalContentColor provides MaterialTheme.colors.secondary
                   ) {
                       text()
                   }
               }
        },
        shape = shape,
        properties = properties
    )
}