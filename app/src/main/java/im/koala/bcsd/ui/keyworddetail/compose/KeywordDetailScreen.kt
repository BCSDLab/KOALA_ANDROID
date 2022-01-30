package im.koala.bcsd.ui.keyworddetail.compose

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.keyworddetail.viewmodel.KeywordDetailViewModel
import im.koala.bcsd.ui.textfield.KoalaSearchField
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import im.koala.domain.repository.KeywordRepository
import im.koala.domain.usecase.keyword.GetKeywordNoticesUseCase
import im.koala.domain.usecase.keyword.KeepSelectedKeywordNoticeUseCase
import im.koala.domain.usecase.keyword.MakeSiteTabItemUseCase
import im.koala.domain.usecase.keyword.RemoveSelectedKeywordNoticeUseCase

@Composable
fun KeywordDetailScreen(
    keywordDetailViewModel: KeywordDetailViewModel,
    onBack: () -> Unit
) {
    BackHandler {
        onBack()
    }

    Scaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                KoalaTextAppBar(
                    title = keywordDetailViewModel.keywordDetailUiState.keyword,
                    onBackClick = onBack,
                    divider = {}
                ) {
                    TextButton(
                        modifier = Modifier.padding(8.dp),
                        onClick = { /* TODO 키워드 수정 화면 전환 */ }
                    ) {
                        Text(
                            text = stringResource(R.string.modify)
                        )
                    }
                }

                KeywordDetailTab(
                    sitePair = keywordDetailViewModel.keywordDetailUiState.keywordSiteTabs,
                    selectedSite = keywordDetailViewModel.keywordDetailUiState.selectedSite,
                    onTabItemSelected = {
                        keywordDetailViewModel.setSite(it)
                    }
                )
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
                text = keywordDetailViewModel.search,
                hint = stringResource(R.string.keyword_detail_search_hint),
                onValueChange = {
                    keywordDetailViewModel.search = it
                },
                onSearch = {
                    keywordDetailViewModel.updateKeywordNotices()
                }
            )

            KeywordNotices(
                keywordNotices = keywordDetailViewModel.keywordDetailUiState.keywordNotices,
                keywordNoticeReadFilter = keywordDetailViewModel.keywordDetailUiState.keywordNoticeReadFilter,
                onKeywordNoticeReadFilterChanged = keywordDetailViewModel::setKeywordNoticeReadFilter,
                onCheckedChange = keywordDetailViewModel::setCheckState,
                onKeepButtonClicked = keywordDetailViewModel::keepSelectedKeywordDetailItem,
                onRemoveButtonClicked = keywordDetailViewModel::removeSelectedKeywordDetailItem
            )
        }
    }
}

private val fakeRepository = object : KeywordRepository {

    override suspend fun getKeywordNotices(
        keyword: String,
        search: String?,
        site: Site?
    ): List<KeywordNotice> {
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

        return list.filter {
            if (search == null) {
                true
            } else {
                it.title.contains(search)
            }
        }.filter {
            if (site == null) true
            else it.site == site
        }
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

    override suspend fun keepSelectedKeywordNotices(keywordNotices: List<KeywordNotice>) {

    }

    override suspend fun removeSelectedKeywordNotices(keywordNotices: List<KeywordNotice>) {

    }
}

private val keywordDetailViewModel = KeywordDetailViewModel(
    GetKeywordNoticesUseCase(fakeRepository),
    MakeSiteTabItemUseCase(fakeRepository),
    KeepSelectedKeywordNoticeUseCase(fakeRepository),
    RemoveSelectedKeywordNoticeUseCase(fakeRepository)
).apply {
    updateKeywordNotices("test")
}

@Preview
@Composable
private fun KeywordDetailScreenPreview() {
    KoalaTheme {
        val activity = LocalContext.current as Activity
        KeywordDetailScreen(
            keywordDetailViewModel
        ) {
            activity.finish()
        }
    }
}