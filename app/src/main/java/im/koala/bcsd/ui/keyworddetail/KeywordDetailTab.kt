package im.koala.bcsd.ui.keyworddetail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun KeywordDetailTab(
    modifier: Modifier = Modifier,
    tabItem: List<String>,
    selectedTabIndex: Int = 0,
    onTabItemSelected: (index: Int, item: String) -> Unit
) {
    ScrollableTabRowWithMinTabWidth(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        edgePadding = 8.dp
    ) {
        tabItem.forEachIndexed { index, string ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabItemSelected(index, string) }) {

                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                    text = string,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = if (index == selectedTabIndex) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}


@Preview
@Composable
private fun KeywordDetailTabPreview() {
    val tabs = listOf("전체", "아우누리", "아우미르", "대신 전해드립니다-Koratech")
    val selectedTabPosition = rememberSaveable { mutableStateOf(0) }

    KoalaTheme {
        Surface {
            KeywordDetailTab(
                modifier = Modifier.fillMaxWidth(),
                tabItem = tabs,
                selectedTabIndex = selectedTabPosition.value,
                onTabItemSelected = { index, item ->
                    selectedTabPosition.value = index
                }
            )
        }
    }
}