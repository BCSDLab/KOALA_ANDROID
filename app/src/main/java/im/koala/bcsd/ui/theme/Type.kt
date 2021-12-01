package im.koala.bcsd.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    h2 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    )
)

@Preview
@Composable
fun TypographyPreview() {
    KoalaTheme {
        Column {
            /*Text(text = "Headline 1", style = MaterialTheme.typography.h1)
            Text(text = "Headline 2", style = MaterialTheme.typography.h2)
            Text(text = "Headline 3", style = MaterialTheme.typography.h3)
            Text(text = "Headline 4", style = MaterialTheme.typography.h4)
            Text(text = "Headline 5", style = MaterialTheme.typography.h5)
            Text(text = "Headline 6", style = MaterialTheme.typography.h6)*/

            Text(text = "Subtitle 1", style = MaterialTheme.typography.subtitle1)
            Text(text = "Subtitle 2", style = MaterialTheme.typography.subtitle2)

            Text(text = "Body 1", style = MaterialTheme.typography.body1)
            Text(text = "Body 2", style = MaterialTheme.typography.body2)

            Text(text = "Button", style = MaterialTheme.typography.button)
            Text(text = "Caption", style = MaterialTheme.typography.caption)
        }
    }
}