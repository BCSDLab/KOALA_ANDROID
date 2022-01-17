package im.koala.bcsd.ui.keyword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import im.koala.bcsd.R
import im.koala.bcsd.ui.textfield.KoalaTextField
import im.koala.bcsd.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun KeywordAddInputScreen(
    navController: NavController,
    textFieldPlaceholder: String,
    tabDataList:List<String>,
    searchText:MutableState<String>,
    recommendationList:List<String>,
    recentSearchList:List<String>,
    searchList:List<String>,
    searchKeyword: (String)->Unit,
    recommendationKeyword: ()->Unit,
    setRecentSearchList: (String)->Unit,
    getRecentSearchList:()->Unit
){
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ){
        KeywordAddInputScreenTopBar(
            keywordText = searchText,
            navController = navController,
            textFieldPlaceholder = textFieldPlaceholder,
            setRecentSearchList = { setRecentSearchList(searchText.value) }
        )
        if(searchText.value.isEmpty()){
            KeyWordAddInputTabBar(
                pagerState = pagerState,
                tabDataList = tabDataList
            )
            recommendationKeyword()
            getRecentSearchList()
            KeyWordAddInputPager(
                keywordText = searchText,
                pagerState = pagerState,
                keywordRecommendationList = recommendationList,
                recentSearchList = recentSearchList
            )
        }
        else{
            searchKeyword(searchText.value)
            KeywordAddInputSearchLazyColumn(searchList,searchText)
        }
    }
}

@Composable
fun KeywordAddInputScreenTopBar(
    keywordText: MutableState<String>,
    navController: NavController,
    textFieldPlaceholder:String,
    setRecentSearchList:(String)->Unit
) {
    KoalaTheme{
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp)
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
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
                value =keywordText.value, onValueChange ={
                    keywordText.value = it
                },
                placeholder = { Text(text = textFieldPlaceholder) },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(50.dp)
                    .background(GrayBorder),
            )
            IconButton(
                onClick = {
                    setRecentSearchList(keywordText.value)
                    navController.navigateUp()
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
fun KeyWordAddInputTabBar(pagerState: PagerState,tabDataList:List<String>) {
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
    keywordText:MutableState<String>,
    pagerState: PagerState,
    keywordRecommendationList:List<String>,
    recentSearchList:List<String>
){
    HorizontalPager(state = pagerState,count = 2) { index ->
        when(index){
            0 -> KeywordAddInputLazyColumn(keywordList = recentSearchList, searchText = keywordText)
            1 -> KeywordAddInputLazyColumn(keywordList = keywordRecommendationList,searchText = keywordText)
        }
    }
}

@Composable
fun KeywordAddInputLazyColumn(keywordList:List<String>, searchText:MutableState<String>){
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(keywordList) {
            Box(Modifier.clickable { searchText.value = it }){
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
fun KeywordAddInputSearchLazyColumn(keywordSearchList:List<String>, searchText:MutableState<String>){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if(keywordSearchList.isEmpty()) item{ Text(text = stringResource(id = R.string.keyword_search_fail), fontSize = 16.sp) }
        else items(keywordSearchList) { KeywordAddItem(text = it, searchText = searchText) }
    }
}

@ExperimentalMaterialApi
@Composable
fun KeywordAddItem(text: String, searchText:MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(GrayBorder)
            .clickable { searchText.value = text },
        verticalAlignment = Alignment.CenterVertically
    ){
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

//@ExperimentalPagerApi
//@ExperimentalMaterialApi
//@Preview("키워드 추가 화면", showBackground = true)
//@Composable
//fun KeywordAddInputScreenPreview(){
//    val navController = rememberNavController()
//    val text = remember {
//        mutableStateOf("")
//    }
//    KeywordAddInputScreen(navController = navController,text)
//}

//@Preview("키워드 추가 화면 상단 바", showBackground = true)
//@Composable
//fun KeywordAddInputScreenTopBarPreview(){
//    val text:MutableState<String> = remember{ mutableStateOf("") }
//    val navController = rememberNavController()
//    KeywordAddInputScreenTopBar(text, navController = navController)
//}

//@ExperimentalMaterialApi
//@Preview("키워드 프레임", showBackground = true)
//@Composable
//fun KeywordAddItemPreview(){
//    KeywordAddItem(text = "아우누리")
//}