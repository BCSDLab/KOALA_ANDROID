package im.koala.bcsd.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import im.koala.bcsd.R
import im.koala.bcsd.navigation.NavScreen
import im.koala.bcsd.ui.keyword.KeywordDetailScreen
import im.koala.bcsd.ui.keyword.*
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.repository.KeywordAddRepository

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val tabStateHolder = HomeTabStateHolder(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState()
    )

    val keywordViewModel = ViewModelProvider(ViewModelStore(), KeyWordViewModelFactory(KeywordAddRepository(LocalContext.current)))
        .get(KeyWordViewModel::class.java)
    val alarmDistinction: MutableState<Boolean> = remember { mutableStateOf(true) }
    val selectAlarmCycle: MutableState<Int> = remember { mutableStateOf(0) }
    val alarmCheckedList: List<MutableState<Boolean>> = arrayListOf(
        remember { mutableStateOf(true) },
        remember { mutableStateOf(true) },
        remember { mutableStateOf(true) }
    )

    val keywordSearchText = remember { mutableStateOf("") }
    val keywordSearchList by keywordViewModel.keywordSearchList.observeAsState(ResponseWrapper(mutableListOf(""),0))
    val keywordRecommendationList by keywordViewModel.keywordRecommendationList.observeAsState(ResponseWrapper(mutableListOf(""),0))
    val recentKeywordSearchList by keywordViewModel.recentKeywordSearchList.observeAsState(listOf(""))

    val deleteSite = remember{ mutableStateOf("") }
    val notificationSiteText = remember { mutableStateOf("") }
    val notificationSiteSearchList by keywordViewModel.keywordSiteSearchList.observeAsState(ResponseWrapper(mutableListOf(""),0))
    val notificationSiteRecommendationList by keywordViewModel.keywordSiteRecommendationList.observeAsState(ResponseWrapper(mutableListOf(""),0))
    val recentSiteSearchList by keywordViewModel.recentSiteSearchList.observeAsState(listOf(""))
    val alarmSiteList by keywordViewModel.alarmSiteList.observeAsState(listOf(""))

    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Keyword.route) {
            composable(
                route = NavScreen.Keyword.route,
                arguments = emptyList()
            ) {
                HomeTabScreen(
                    viewModel = hiltViewModel(),
                    tabStateHolder = tabStateHolder,
                    selectItem = { tab, index ->
                        when (tab) {
                            MainScreenBottomTab.KEYWORD -> navController.navigate("${NavScreen.KeywordDetails.route}/$index")
                            MainScreenBottomTab.HISTORY -> navController.navigate("${NavScreen.HistoryDetails.route}/$index")
                            MainScreenBottomTab.CHATROOM -> navController.navigate("${NavScreen.ChatRoomDetails.route}/$index")
                            MainScreenBottomTab.SETTING -> navController.navigate("${NavScreen.SettingDetails.route}/$index")
                        }
                    },
                    navController
                )
            }

            composable(
                route = NavScreen.KeywordAdd.route
            ){
                KeywordAddScreen(
                    screenName = stringResource(id = R.string.keyword_add),
                    navController = navController,
                    selectAlarmCycle = selectAlarmCycle,
                    alarmDistinction = alarmDistinction,
                    keywordSearchText = keywordSearchText,
                    notificationSiteText = notificationSiteText,
                    deleteSite = deleteSite,
                    alarmSiteList = alarmSiteList,
                    alarmCheckedList = alarmCheckedList,
                    getAlarmSiteList = { keywordViewModel.getAlarmSiteList() },
                    setAlarmSiteList = { keywordViewModel.setAlarmSiteList(notificationSiteText.value) },
                    deleteSiteList = { keywordViewModel.deleteSiteList(deleteSite.value) },
                    resetSiteList = { keywordViewModel.resetSiteList() }
                ) {
                    keywordViewModel.pushKeyword(
                        alarmCycle = selectAlarmCycle.value,
                        alarmMode = alarmCheckedList[0].value,
                        isImportant = alarmDistinction.value,
                        name = keywordSearchText.value,
                        siteList = alarmSiteList,
                        untilPressOkButton = alarmCheckedList[2].value,
                        vibrationMode = alarmCheckedList[1].value
                    )
                }
            }

            composable(
                route = NavScreen.KeywordAddInput.route
            ){
                KeywordAddInputScreen(
                    navController = navController,
                    textFieldPlaceholder = stringResource(id = R.string.keyword_input),
                    tabDataList = mutableListOf("최근 검색","추천 키워드"),
                    searchText = keywordSearchText,
                    recommendationList = keywordRecommendationList.body,
                    recentSearchList = recentKeywordSearchList,
                    searchList = keywordSearchList.body,
                    searchKeyword = { keywordViewModel.getKeywordSearchList(keywordSearchText.value) },
                    recommendationKeyword = { keywordViewModel.getKeywordRecommendation() },
                    setRecentSearchList = { keywordViewModel.setRecentKeywordSearchList(keywordSearchText.value) },
                    getRecentSearchList = { keywordViewModel.getRecentKeywordSearchList() }
                )
            }

            composable(
                route = NavScreen.KeywordSiteAddInput.route
            ){
                KeywordAddInputScreen(
                    navController = navController,
                    textFieldPlaceholder = stringResource(id = R.string.search_for_notifications_input),
                    tabDataList = mutableListOf("최근 검색","추천 대상"),
                    searchText = notificationSiteText,
                    recommendationList = notificationSiteRecommendationList.body,
                    searchList = notificationSiteSearchList.body,
                    recentSearchList = recentSiteSearchList,
                    searchKeyword = { keywordViewModel.getKeywordSiteSearch(notificationSiteText.value) },
                    recommendationKeyword = { keywordViewModel.getKeywordSiteRecommendation() },
                    setRecentSearchList = { keywordViewModel.setRecentSiteSearchList(notificationSiteText.value) },
                    getRecentSearchList = { keywordViewModel.getRecentSiteSearchList() }
                )
            }
            composable(
                route = NavScreen.KeywordDetails.routeWithArgument,
                arguments = listOf(
                    navArgument(NavScreen.KeywordDetails.argument0) { type = NavType.LongType }
                )
            ) { backStackEntry ->

                val posterId =
                    backStackEntry.arguments?.getLong(NavScreen.KeywordDetails.argument0)
                        ?: return@composable

                KeywordDetailScreen() {
                    navController.navigateUp()
                }
            }
        }
    }
}

@Immutable
enum class MainScreenBottomTab(
    @StringRes val title: Int,
    @DrawableRes val drawableId: Int
) {
    KEYWORD(R.string.keyword, R.drawable.ic_hashtag),
    HISTORY(R.string.history, R.drawable.ic_history),
    CHATROOM(R.string.chatroom, R.drawable.ic_chat_room),
    SETTING(R.string.setting, R.drawable.ic_setting)
}
