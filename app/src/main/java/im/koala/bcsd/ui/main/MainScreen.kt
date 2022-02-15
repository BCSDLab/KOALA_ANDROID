package im.koala.bcsd.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import im.koala.bcsd.R
import im.koala.bcsd.navigation.NavScreen
import im.koala.bcsd.ui.keyword.KeywordDetailScreen
import im.koala.bcsd.ui.keywordadd.KeywordAddInputScreen
import im.koala.bcsd.ui.keywordadd.KeywordAddScreen
import im.koala.bcsd.ui.keywordadd.KeywordViewModel

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    keywordViewModel: KeywordViewModel = viewModel(),
    viewModel: MainViewModel = viewModel()
) {
    val navController = rememberNavController()
    val tabStateHolder = HomeTabStateHolder(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState()
    )
    val keywordSearchText = remember { mutableStateOf("") }
    val alarmSiteText = remember { mutableStateOf("") }

    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Keyword.route) {
            composable(
                route = NavScreen.Keyword.route,
                arguments = emptyList()
            ) {
                HomeTabScreen(
                    viewModel = viewModel,
                    tabStateHolder = tabStateHolder,
                    selectItem = { tab, index ->
                        when (tab) {
                            MainScreenBottomTab.KEYWORD -> navController.navigate("${NavScreen.KeywordDetails.route}/$index")
                            MainScreenBottomTab.HISTORY -> navController.navigate("${NavScreen.HistoryDetails.route}/$index")
                            MainScreenBottomTab.CHATROOM -> navController.navigate("${NavScreen.ChatRoomDetails.route}/$index")
                            MainScreenBottomTab.SETTING -> navController.navigate("${NavScreen.SettingDetails.route}/$index")
                        }
                    },
                    navController = navController
                )
            }

            composable(
                route = NavScreen.KeywordAdd.route
            ) {
                val alarmDistinction: MutableState<Boolean> = remember { mutableStateOf(true) }
                val selectAlarmCycle: MutableState<Int> = remember { mutableStateOf(0) }
                val alarmCheckedList: List<MutableState<Boolean>> = arrayListOf(
                    remember { mutableStateOf(true) },
                    remember { mutableStateOf(true) },
                    remember { mutableStateOf(true) }
                )
                val deleteSite = remember { mutableStateOf("") }
                val alarmSiteList by keywordViewModel.alarmSiteList.observeAsState(listOf(""))
                val keywordNameList by keywordViewModel.keywordNameList.observeAsState(listOf(""))

                keywordViewModel.getAlarmSiteList()
                keywordViewModel.getKeywordNameList()

                KeywordAddScreen(
                    screenName = stringResource(id = R.string.keyword_add),
                    navController = navController,
                    selectAlarmCycle = selectAlarmCycle,
                    alarmDistinction = alarmDistinction,
                    keywordSearchText = keywordSearchText,
                    alarmSiteText = alarmSiteText,
                    deleteSite = deleteSite,
                    alarmSiteList = alarmSiteList,
                    alarmCheckedList = alarmCheckedList,
                    keywordNameList = keywordNameList,
                    addAlarmSiteList = { keywordViewModel.addAlarmSiteList(alarmSiteText.value) },
                    deleteAlarmSite = { keywordViewModel.deleteAlarmSite(deleteSite.value) },
                    deleteAllAlarmSiteList = { keywordViewModel.deleteAllSiteList() },
                ) {
                    viewModel.pushKeyword(
                        alarmCycle = selectAlarmCycle.value,
                        alarmMode = alarmCheckedList[0].value,
                        isImportant = alarmDistinction.value,
                        name = keywordSearchText.value,
                        untilPressOkButton = alarmCheckedList[2].value,
                        vibrationMode = alarmCheckedList[1].value,
                        alarmSiteList = alarmSiteList
                    )
                }
            }

            composable(
                route = NavScreen.KeywordAddInput.route
            ) {
                val keywordSearchingText = remember { mutableStateOf("") }
                val keywordRecommendationList by keywordViewModel.keywordRecommendationList.observeAsState(
                    listOf("")
                )
                val keywordSearchList by keywordViewModel.keywordSearchList.observeAsState(listOf(""))
                val recentKeywordSearchList by keywordViewModel.recentKeywordSearchList.observeAsState(
                    listOf("")
                )

                keywordViewModel.getKeywordRecommendation()
                keywordViewModel.getRecentKeywordSearchList()

                KeywordAddInputScreen(
                    navController = navController,
                    errorMessage = stringResource(id = R.string.keyword_search_empty),
                    textFieldPlaceholder = stringResource(id = R.string.keyword_input),
                    tabDataList = mutableListOf("최근 검색", "추천 키워드"),
                    searchingText = keywordSearchingText,
                    searchText = keywordSearchText,
                    recommendationList = keywordRecommendationList,
                    recentSearchList = recentKeywordSearchList,
                    searchList = keywordSearchList,
                    getSearchList = { keywordViewModel.getKeywordSearchList(keywordSearchingText.value) },
                    setRecentSearchList = {
                        keywordViewModel.setRecentKeywordSearchList(
                            keywordSearchText.value
                        )
                    },
                )
            }

            composable(
                route = NavScreen.KeywordSiteAddInput.route
            ) {
                val siteSearchingText = remember { mutableStateOf("") }
                val alarmSiteRecommendationList by keywordViewModel.siteRecommendationList.observeAsState(
                    listOf("")
                )
                val alarmSiteSearchList by keywordViewModel.siteSearchList.observeAsState(listOf(""))
                val recentSiteSearchList by keywordViewModel.recentSiteSearchList.observeAsState(
                    listOf("")
                )

                keywordViewModel.getSiteRecommendation()
                keywordViewModel.getRecentSiteSearchList()

                KeywordAddInputScreen(
                    navController = navController,
                    errorMessage = stringResource(id = R.string.site_search_empty),
                    textFieldPlaceholder = stringResource(id = R.string.search_for_notifications_input),
                    tabDataList = mutableListOf("최근 검색", "추천 대상"),
                    searchingText = siteSearchingText,
                    searchText = alarmSiteText,
                    recommendationList = alarmSiteRecommendationList,
                    searchList = alarmSiteSearchList,
                    recentSearchList = recentSiteSearchList,
                    getSearchList = { keywordViewModel.getSiteSearchList(siteSearchingText.value) },
                    setRecentSearchList = { keywordViewModel.setRecentSiteSearchList(alarmSiteText.value) },
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