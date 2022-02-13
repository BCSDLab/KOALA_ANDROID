package im.koala.bcsd.ui.keyword

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager
import im.koala.bcsd.R
import im.koala.bcsd.ui.textfield.KoalaTextField
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.ui.theme.White
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun KeywordAddInputScreen(
    navController: NavController,
    textFieldPlaceholder: String,
    errorMessage: String,
    tabDataList: List<String>,
    searchingText: MutableState<String>,
    searchText: MutableState<String>,
    recommendationList: List<String>,
    recentSearchList: List<String>,
    searchList: List<String>,
    getSearchList: () -> Unit,
    setRecentSearchList: () -> Unit,
) {
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        KeywordAddInputScreenTopBar(
            keywordText = searchText,
            searchingText = searchingText,
            navController = navController,
            textFieldPlaceholder = textFieldPlaceholder,
            errorMessage = errorMessage,
            setRecentSearchList = { setRecentSearchList() }
        )
        if (searchingText.value.isEmpty()) {
            KeyWordAddInputTabBar(
                pagerState = pagerState,
                tabDataList = tabDataList
            )
            KeyWordAddInputPager(
                searchingText = searchingText,
                pagerState = pagerState,
                keywordRecommendationList = recommendationList,
                recentSearchList = recentSearchList
            )
        } else {
            getSearchList()
            KeywordAddInputSearchLazyColumn(searchList, searchingText)
        }
    }
}

@Composable
fun KeywordAddInputScreenTopBar(
    keywordText: MutableState<String>,
    searchingText: MutableState<String>,
    navController: NavController,
    textFieldPlaceholder: String,
    errorMessage: String,
    setRecentSearchList: () -> Unit
) {
    val context = LocalContext.current
    KoalaTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
        ) {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_nav_back),
                    contentDescription = "",
                    modifier = Modifier
                        .size(28.dp)
                )
            }
            KoalaTextField(
                value = searchingText.value,
                onValueChange = {
                    searchingText.value = it
                },
                placeholder = { Text(text = textFieldPlaceholder) },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(50.dp)
                    .background(GrayBorder),
            )
            IconButton(
                onClick = {
                    if (searchingText.value.isEmpty()) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    } else {
                        keywordText.value = searchingText.value
                        setRecentSearchList()
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_hashtag),
                    contentDescription = "",
                    modifier = Modifier
                        .background(Black)
                        .size(50.dp)
                        .padding(10.dp),
                    tint = White
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun KeyWordAddInputTabBar(pagerState: PagerState, tabDataList: List<String>) {
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
        selectedTabIndex = tabIndex,
        backgroundColor = White,
        divider = {
            TabRowDefaults.Divider(
                thickness = 1.dp,
                color = GrayBorder
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 3.dp,
                color = Black,
            )
        }
    ) {
        tabDataList.forEachIndexed { index, text ->
            Tab(
                selected = tabIndex == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = text,
                        color = Black,
                        textAlign = TextAlign.Left
                    )
                })
        }
    }
}

@ExperimentalPagerApi
@Composable
fun KeyWordAddInputPager(
    searchingText: MutableState<String>,
    pagerState: PagerState,
    keywordRecommendationList: List<String>,
    recentSearchList: List<String>
) {
    HorizontalPager(state = pagerState, count = 2) { index ->
        when (index) {
            0 -> KeywordAddInputLazyColumn(
                searchingList = recentSearchList,
                searchingText = searchingText
            )
            1 -> KeywordAddInputLazyColumn(
                searchingList = keywordRecommendationList,
                searchingText = searchingText
            )
        }
    }
}

@Composable
fun KeywordAddInputLazyColumn(searchingList: List<String>, searchingText: MutableState<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(searchingList) {
            Box(Modifier.clickable { searchingText.value = it }) {
                Text(
                    text = it,
                    fontSize = 17.sp,
                    color = Black
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun KeywordAddInputSearchLazyColumn(
    keywordSearchList: List<String>,
    searchText: MutableState<String>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (keywordSearchList.isEmpty()) item {
            Text(
                text = stringResource(id = R.string.keyword_search_fail),
                fontSize = 16.sp
            )
        }
        else items(keywordSearchList) { KeywordAddItem(text = it, searchText = searchText) }
    }
}

@ExperimentalMaterialApi
@Composable
fun KeywordAddItem(text: String, searchText: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(GrayBorder)
            .clickable { searchText.value = text },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_hashtag),
            contentDescription = "",
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            fontSize = 15.sp
        )
    }
}