package im.koala.bcsd.ui.indicator

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun KoalaDotIndicator(
    modifier: Modifier = Modifier,
    dotCount: Int,
    dotPosition: Int,
    dotPadding: Dp = 24.dp
) {
    val yellowDotPadding: Dp by animateDpAsState(dotPadding * dotPosition)

    Box(
        modifier = modifier
    ) {
        for (i in 0 until dotCount) {
            Dot(
                modifier = Modifier.absoluteOffset(x = dotPadding * i),
                dotColor = GrayDisabled
            )
        }

        Dot(
            modifier = Modifier.absoluteOffset(x = yellowDotPadding),
            dotColor = MaterialTheme.colors.onError
        )
    }
}

@Composable
private fun Dot(
    modifier: Modifier = Modifier,
    dotColor: Color
) {
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(dotColor)
    )
}

@Preview("Koala Dot Indicator")
@Composable
fun KoalaDotIndicatorPreview() {
    val dotPosition = rememberSaveable { mutableStateOf(0) }

    KoalaTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            KoalaDotIndicator(
                modifier = Modifier.padding(16.dp),
                dotCount = 6,
                dotPosition = dotPosition.value
            )
            Row {
                Button(onClick = { dotPosition.value-- }) {
                    Text(text = "-")
                }
                Button(onClick = { dotPosition.value++ }) {
                    Text(text = "+")
                }
            }
        }
    }
}