package im.koala.bcsd.ui.keyworddetail

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
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
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import im.koala.domain.usecase.keyword.GetKeywordItemListUseCase
import im.koala.domain.usecase.keyword.MakeSiteTabItemUseCase

@Composable
fun KeywordDetailScreen(
    keywordDetailViewModel: KeywordDetailViewModel,
    onBack: () -> Unit
) {
    val search = rememberSaveable { mutableStateOf("") }

    val selectedTabIndex = rememberSaveable { mutableStateOf(0) }

    val popupMenuExpanded = rememberSaveable { mutableStateOf(false) }

    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                KoalaTextAppBar(
                    title = keywordDetailViewModel.keyword,
                    onBackClick = onBack,
                    divider = {}
                ) {
                    TextButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { /* TODO 키워드 수정 버튼 클릭 */ }
                    ) {
                        Text(
                            text = stringResource(R.string.modify)
                        )
                    }
                }

                KeywordDetailTab(
                    tabItem = keywordDetailViewModel.keywordTabs.values,
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
                onSearch = {
                    keywordDetailViewModel.fetchDetailListItems(keywordDetailViewModel.keyword)
                }
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
                    KoalaCheckBox(checked = keywordDetailViewModel.selectedAll, onCheckedChange = { isChecked ->
                        keywordDetailViewModel.selectedAll = isChecked
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
                        iconPainter = painterResource(id = R.drawable.ic_inbox_in),
                        onClick = keywordDetailViewModel::keepSelectedKeywordDetailItem
                    )

                    KeywordScreenBackgroundButton(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(R.string.keyword_detail_item_delete),
                        iconPainter = painterResource(id = R.drawable.ic_trash),
                        onClick = keywordDetailViewModel::removeSelectedKeywordDetailItem
                    )

                    IconButton(onClick = {
                        popupMenuExpanded.value = true
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dots_vertical),
                            contentDescription = ""
                        )

                        KeywordDetailPopupMenu(
                            expanded = popupMenuExpanded.value,
                            keywordItemReadFilter = keywordDetailViewModel.filterState.keywordItemReadFilter,
                            onDismissRequest = { popupMenuExpanded.value = false },
                            onSelectionChange = {
                                keywordDetailViewModel.setKeywordItemReadFilter(it)
                            }
                        )
                    }
                }
            }

            LazyColumn {
                itemsIndexed(keywordDetailViewModel.keywordNotices) { index, keywordListItem ->
                    KeywordDetailItem(
                        title = keywordDetailViewModel.keywordTabs[keywordListItem.site] ?: "",
                        content = keywordListItem.title,
                        date = keywordListItem.createdAt,
                        onCheckedChange = { isChecked ->
                            keywordDetailViewModel.setCheckState(keywordListItem, isChecked)
                        },
                        isRead = keywordListItem.isRead,
                        isChecked = keywordListItem.isChecked
                    )
                }
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
    val fakeRepository = object : KeywordRepository {
        override fun getKeywordNotices(keyword: String): List<KeywordNotice> {
            val list = mutableListOf<KeywordNotice>()
            val sites = arrayOf(Site.Dorm, Site.Facebook, Site.Instagram, Site.Portal, Site.Youtube)

            (1..20).forEach {
                list.add(
                    KeywordNotice(
                        id = it,
                        site = sites[it % sites.size],
                        title = "Keyword #$it",
                        url = "",
                        createdAt = "2022-01-${String.format("%02d", it)}",
                        isRead = it % 2 == 0,
                        isChecked = false
                    )
                )
            }

            return list.toList()
        }

        override fun getSiteLocalizedMessage(site: Site): String {
            return when (site) {
                Site.All -> "전체"
                Site.Dorm -> "아우미르"
                Site.Facebook -> "페이스북"
                Site.Instagram -> "인스타그램"
                Site.Portal -> "아우누리"
                Site.Youtube -> "유튜브"
            }
        }

        override fun keepSelectedKeywordNotices(keywordDetailItems: List<KeywordNotice>) {

        }

        override fun removeSelectedKeywordNotices(keywordDetailItems: List<KeywordNotice>) {

        }
    }

    val keywordDetailViewModel = KeywordDetailViewModel(
        GetKeywordItemListUseCase(fakeRepository),
        MakeSiteTabItemUseCase(fakeRepository)
    )

    keywordDetailViewModel.fetchDetailListItems("Skybodakoreatech")

    KoalaTheme {
        val activity = LocalContext.current as Activity
        KeywordDetailScreen(
            keywordDetailViewModel
        ) {
            activity.finish()
        }
    }

}