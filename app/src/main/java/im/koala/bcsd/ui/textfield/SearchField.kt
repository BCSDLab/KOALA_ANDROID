package im.koala.bcsd.ui.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaButton
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun KoalaSearchField(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onValueChange: (String) -> Unit,
    onSearch: (search: String) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(end = this.maxHeight)
                .background(GrayBorder)
                .padding(8.dp),
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            maxLines = 1,
        ) { textField ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (text.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = hint,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground,
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    textField()
                }
            }
        }
        KoalaButton(
            modifier = Modifier
                .height(this.maxHeight)
                .width(this.maxHeight),
            onClick = { onSearch(text) }
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_search_field_search),
                contentDescription = ""
            )
        }
    }
}

@Preview("Koala search field with no input")
@Composable
private fun KoalaSearchFieldWithNoInput() {
    val text = rememberSaveable { mutableStateOf("") }
    KoalaTheme {
        KoalaSearchField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(40.dp),
            text = text.value,
            hint = "알림 대상 / 알림 내용 / 키워드 입력",
            onValueChange = { text.value = it },
            onSearch = {}
        )
    }
}

@Preview("Koala search field with input")
@Composable
private fun KoalaSearchFieldWithInput() {
    val text = rememberSaveable { mutableStateOf("SKY boda KOREATECHHHHHHHHHHHHHHHHHHHHHHHHHH") }
    KoalaTheme {
        KoalaSearchField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(40.dp),
            text = text.value,
            hint = "알림 대상 / 알림 내용 / 키워드 입력",
            onValueChange = { text.value = it },
            onSearch = {}
        )
    }
}