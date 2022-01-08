package im.koala.bcsd.ui.keyworddetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.textfield.KoalaSearchField
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun KeywordDetailScreen(
    keyword: String,
    keywordSources: List<String>,
    onBack: () -> Unit
) {
    val tabItems = listOf(stringResource(R.string.keyword_detail_tab_item_all)) +
        keywordSources

    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }
    val search = rememberSaveable { mutableStateOf("") }

    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                KoalaTextAppBar(
                    title = keyword,
                    onBackClick = onBack,
                    divider = {}
                ) {
                    TextButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { /* TODO */ }
                    ) {
                        Text(
                            text = stringResource(R.string.modify)
                        )
                    }
                }

                KeywordDetailTab(
                    tabItem = tabItems,
                    selectedTabIndex = selectedTabIndex.value,
                    onTabItemSelected = { index, item ->
                        selectedTabIndex.value = index
                    })
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            KoalaSearchField(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                text = search.value,
                hint = stringResource(R.string.keyword_detail_search_hint),
                onValueChange = {
                    search.value = it
                },
                onSearch = { /* TODO */ }
            )
        }
    }
}

@Preview
@Composable
private fun KeywordDetailScreenPreview() {
    KoalaTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            KeywordDetailScreen(keyword = "테스트",
                onBack = {},
            keywordSources = listOf("아우누리", "아우미르", "대신 전해드립니다-Koratech"))
        }
    }
}