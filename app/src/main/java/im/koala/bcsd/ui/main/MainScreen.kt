package im.koala.bcsd.ui.main

import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
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
import im.koala.data.api.response.keywordadd.KeywordAddResponseUi

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(
    keywordViewModel: KeywordViewModel = hiltViewModel(),
    viewModel: MainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val tabStateHolder = HomeTabStateHolder(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState()
    )
    val keywordSearchText by keywordViewModel.searchingKeyword.observeAsState("")
    val siteSearchText by keywordViewModel.searchingSite.observeAsState("")
    val alarmDistinction: MutableState<Boolean> = remember { mutableStateOf(true) }
    val selectAlarmCycle: MutableState<Int> = remember { mutableStateOf(0) }
    val alarmCheckedList: List<MutableState<Boolean>> = arrayListOf(
        remember { mutableStateOf(true) },
        remember { mutableStateOf(true) },
        remember { mutableStateOf(true) }
    )
    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Keyword.route) {
            composable(
                route = NavScreen.Keyword.route,
                arguments = emptyList()
            ) {
                keywordViewModel.deleteAllSiteList()
                keywordViewModel.setSearchKeyword("")
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
                    navController = navController,
                    keywordViewModel = keywordViewModel
                )
            }

            composable(
                route = NavScreen.KeywordAdd.route
            ) {
                val deleteSite = remember { mutableStateOf("") }
                val alarmSiteList by keywordViewModel.alarmSiteList.observeAsState(listOf(""))
                val keywordNameList by keywordViewModel.keywordNameList.observeAsState(listOf(""))
                alarmDistinction.value = true
                selectAlarmCycle.value = 0
                alarmCheckedList[0].value = true
                alarmCheckedList[1].value = true
                alarmCheckedList[2].value = true
                keywordViewModel.getAlarmSiteList()
                keywordViewModel.getKeywordNameList()

                KeywordAddScreen(
                    screenName = stringResource(id = R.string.keyword_add),
                    navController = navController,
                    selectAlarmCycle = selectAlarmCycle,
                    alarmDistinction = alarmDistinction,
                    keywordSearchText = keywordSearchText,
                    alarmSiteText = siteSearchText,
                    deleteSite = deleteSite,
                    alarmSiteList = alarmSiteList,
                    alarmCheckedList = alarmCheckedList,
                    keywordNameList = keywordNameList,
                    addAlarmSiteList = { keywordViewModel.addAlarmSiteList(siteSearchText) },
                    deleteAlarmSite = { keywordViewModel.deleteAlarmSite(deleteSite.value) },
                    setSite = { keywordViewModel.setSearchSite(it) },
                    setKeyword = { keywordViewModel.setSearchKeyword(it) },
                ) {
                    viewModel.pushKeyword(
                        alarmCycle = selectAlarmCycle.value,
                        alarmMode = alarmCheckedList[0].value,
                        isImportant = alarmDistinction.value,
                        name = keywordSearchText,
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
                    recommendationList = keywordRecommendationList,
                    recentSearchList = recentKeywordSearchList,
                    searchList = keywordSearchList,
                    getSearchList = { keywordViewModel.getKeywordSearchList(keywordSearchingText.value) },
                    setRecentSearchList = {
                        keywordViewModel.setRecentKeywordSearchList(
                            keywordSearchText
                        )
                    },
                    setKeyword = { keywordViewModel.setSearchKeyword(keywordSearchingText.value) }
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
                    recommendationList = alarmSiteRecommendationList,
                    searchList = alarmSiteSearchList,
                    recentSearchList = recentSiteSearchList,
                    getSearchList = { keywordViewModel.getSiteSearchList(siteSearchingText.value) },
                    setRecentSearchList = { keywordViewModel.setRecentSiteSearchList(siteSearchText) },
                    setKeyword = { keywordViewModel.setSearchSite(siteSearchingText.value) }
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

            composable(
                route = NavScreen.KeywordEdit.route,
            ) {
                val uiState by keywordViewModel.uiState.observeAsState(KeywordAddResponseUi())
                alarmDistinction.value = uiState.isImportant
                selectAlarmCycle.value = uiState.alarmCycle
                alarmCheckedList[0].value = uiState.silentMode
                alarmCheckedList[1].value = uiState.vibrationMode
                alarmCheckedList[2].value = uiState.untilPressOkButton
                val alarmSiteList by keywordViewModel.alarmSiteList.observeAsState(uiState.siteList)
                val deleteSite = remember { mutableStateOf("") }
                val keywordNameList by keywordViewModel.keywordNameList.observeAsState(listOf(""))
                keywordViewModel.getAlarmSiteList()
                keywordViewModel.getKeywordNameList()
                keywordViewModel.setSearchKeyword(keywordSearchText)
                Log.d("KeywordAddViewModel","siteList: $alarmSiteList, nameList: $keywordNameList")
                Log.d("KeywordAddViewModel","keyword: $keywordSearchText")

                KeywordAddScreen(
                    screenName = stringResource(id = R.string.edit_keyword),
                    navController = navController,
                    selectAlarmCycle = selectAlarmCycle,
                    alarmDistinction = alarmDistinction,
                    keywordSearchText = keywordSearchText,
                    alarmSiteText = siteSearchText,
                    deleteSite = deleteSite,
                    alarmSiteList = alarmSiteList,
                    alarmCheckedList = alarmCheckedList,
                    keywordNameList = keywordNameList,
                    addAlarmSiteList = { keywordViewModel.addAlarmSiteList(siteSearchText) },
                    deleteAlarmSite = { keywordViewModel.deleteAlarmSite(deleteSite.value) },
                    setKeyword = { keywordViewModel.setSearchKeyword(it) },
                    setSite = { keywordViewModel.setSearchSite(it) },
                    pushKeyword = {
                        viewModel.editKeyword(
                            keyword = uiState.name,
                            alarmCycle = selectAlarmCycle.value,
                            alarmMode = alarmCheckedList[0].value,
                            isImportant = alarmDistinction.value,
                            name = keywordSearchText,
                            untilPressOkButton = alarmCheckedList[2].value,
                            vibrationMode = alarmCheckedList[1].value,
                            alarmSiteList = alarmSiteList
                        )
                    },
                )
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