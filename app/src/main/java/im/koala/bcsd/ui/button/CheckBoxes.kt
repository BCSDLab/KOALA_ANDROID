package im.koala.bcsd.ui.button

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.ui.theme.Yellow

@Composable
fun KoalaCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Checkbox(
        checked, onCheckedChange, modifier, enabled, interactionSource,
        CheckboxDefaults.colors(
            checkedColor = Yellow
        )
    )
}

@Composable
fun KoalaCircularCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clickable { onCheckedChange(!checked) }
    ) {
        if (checked) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Yellow)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = GrayDisabled,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun KoalaToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    iconModifier:Modifier = Modifier
) {

    Box(
        modifier = modifier
//            .width(22.dp)
//            .height(12.dp)
//            .clip(RoundedCornerShape(50))
//            .background(
//                GrayDisabled
//            )
            .clickable { onCheckedChange(!checked) }
    ) {
        KoalaToggleIcon(
            modifier = iconModifier
//                .padding(start = paddingStart, top = 1.dp, bottom = 1.dp)
        )
    }
}

@Composable
private fun KoalaToggleIcon(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
//            .size(10.dp)
//            .clip(CircleShape)
//            .background(Yellow)
    )
}

@Preview
@Composable
private fun KoalaToggleIconPreview() {
    KoalaTheme {
        KoalaToggleIcon(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Yellow)
        )
    }
}

@Preview
@Composable
private fun KoalaCheckBoxPreview() {
    val isChecked1 = remember { mutableStateOf(true) }
    val isChecked2 = remember { mutableStateOf(false) }
    KoalaTheme {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        ) {
            KoalaCheckBox(
                checked = isChecked1.value,
                onCheckedChange = { isChecked1.value = !isChecked1.value },
                modifier = Modifier.padding(16.dp)
            )
            KoalaCheckBox(
                checked = isChecked2.value,
                onCheckedChange = { isChecked2.value = !isChecked2.value },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun KoalaCircularCheckBoxPreview() {
    val isChecked1 = remember { mutableStateOf(true) }
    val isChecked2 = remember { mutableStateOf(false) }
    KoalaTheme {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        ) {
            KoalaCircularCheckBox(
                checked = isChecked1.value,
                onCheckedChange = { isChecked1.value = !isChecked1.value },
                modifier = Modifier.padding(16.dp)
            )
            KoalaCircularCheckBox(
                checked = isChecked2.value,
                onCheckedChange = { isChecked2.value = !isChecked2.value },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun KoalaToggleButtonPreview() {
    val isChecked1 = remember { mutableStateOf(true) }
    val isChecked2 = remember { mutableStateOf(false) }
    val paddingStart1: Dp by animateDpAsState(targetValue = if (isChecked1.value) 11.dp else 1.dp)
    val paddingStart2: Dp by animateDpAsState(targetValue = if (isChecked2.value) 11.dp else 1.dp)

    KoalaTheme {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(16.dp)
        ) {
            KoalaToggle(
                checked = isChecked1.value,
                onCheckedChange = { isChecked1.value = !isChecked1.value },
                modifier = Modifier
                    .padding(16.dp)
                    .width(22.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(50))
                    .background(GrayDisabled),
                iconModifier = Modifier
                    .padding(start = paddingStart1, top = 1.dp, bottom = 1.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Yellow)
            )
            KoalaToggle(
                checked = isChecked2.value,
                onCheckedChange = { isChecked2.value = !isChecked2.value },
                modifier = Modifier
                    .padding(16.dp)
                    .width(22.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(50))
                    .background(GrayDisabled),
                iconModifier = Modifier
                    .padding(start = paddingStart2, top = 1.dp, bottom = 1.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Yellow)
            )
        }
    }
}