package im.koala.bcsd.ui.tutorial

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.button.KoalaCircularCheckBox
import im.koala.bcsd.ui.theme.GrayDisabled

@Composable
fun TutorialProcessDot(pageList: List<Boolean>) {
    Row(
        modifier = Modifier
            .width(80.dp)
    ) {
        for (element in pageList) {
            if (element) {
                KoalaCircularCheckBox(
                    checked = element,
                    onCheckedChange = {},
                    modifier = Modifier.size(8.dp)
                )
            } else {
                KoalaCircularCheckBox(
                    checked = element,
                    onCheckedChange = {},
                    modifier = Modifier
                        .size(8.dp)
                        .border(8.dp, color = GrayDisabled, shape = CircleShape)
                )
            }

            Spacer(modifier = Modifier.padding(start = 16.dp))
        }
    }
}

@Preview(name = "1번 페이지")
@Composable
fun Preview1() {
    TutorialProcessDot(listOf(true, false, false, false))
}

@Preview(name = "2번 페이지")
@Composable
fun Preview2() {
    TutorialProcessDot(listOf(false, true, false, false))
}

@Preview(name = "3번 페이지")
@Composable
fun Preview3() {
    TutorialProcessDot(listOf(false, false, true, false))
}

@Preview(name = "4번 페이지")
@Composable
fun Preview4() {
    TutorialProcessDot(listOf(false, false, false, true))
}