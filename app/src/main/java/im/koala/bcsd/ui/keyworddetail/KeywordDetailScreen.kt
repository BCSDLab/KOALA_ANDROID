package im.koala.bcsd.ui.keyworddetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.button.KoalaBackgroundButton
import im.koala.bcsd.ui.button.KoalaCheckBox
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
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                text = search.value,
                hint = stringResource(R.string.keyword_detail_search_hint),
                onValueChange = {
                    search.value = it
                },
                onSearch = { /* TODO */ }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KoalaCheckBox(checked = true, onCheckedChange = {

                    })
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.keyword_detail_select_all)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    KeywordScreenBackgroundButton(
                        text = stringResource(R.string.keyword_detail_item_inbox),
                        iconPainter = painterResource(id = R.drawable.ic_inbox_in)
                    ) {
                        //키워드 아이템 보관 버튼 onclick
                    }

                    KeywordScreenBackgroundButton(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(R.string.keyword_detail_item_delete),
                        iconPainter = painterResource(id = R.drawable.ic_trash)
                    ) {
                        //키워드 아이템 삭제 버튼 onclick
                    }

                    IconButton(onClick = {
                        //키워드 아이템 ...버튼 onclick
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dots_vertical),
                            contentDescription = ""
                        )
                    }
                }
            }

            LazyColumn {

            }
        }
    }
}

@Composable
private fun KeywordScreenBackgroundButton(
    modifier: Modifier = Modifier,
    text: String,
    iconPainter: Painter,
    onClick: () -> Unit
) {
    KoalaBackgroundButton(
        modifier = modifier.height(34.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = iconPainter, contentDescription = "")
            Text(text = text)
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
            KeywordDetailScreen(
                keyword = "테스트",
                onBack = {},
                keywordSources = listOf("아우누리", "아우미르", "대신 전해드립니다-Koratech")
            )
        }
    }
}