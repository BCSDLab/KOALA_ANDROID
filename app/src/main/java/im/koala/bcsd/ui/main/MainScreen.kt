package im.koala.bcsd.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Keyword.route) {
            composable(
                route = NavScreen.Keyword.route,
                arguments = emptyList()
            ) {
                keywordViewModel.initState()
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
                )
            }

            composable(
                route = NavScreen.KeywordAdd.route
            ) {
                val deleteSite = remember { mutableStateOf("") }
                KeywordAddScreen(
                    keywordViewModel = keywordViewModel,
                    screenName = stringResource(id = R.string.keyword_add),
                    navController = navController,
                    deleteSite = deleteSite,
                ) {
                    viewModel.pushKeyword(
                        alarmCycle = keywordViewModel.uiState.alarmCycle,
                        alarmMode = keywordViewModel.uiState.silentMode,
                        isImportant = keywordViewModel.uiState.isImportant,
                        name = keywordViewModel.uiState.keyword,
                        untilPressOkButton = keywordViewModel.uiState.untilPressOkButton,
                        vibrationMode = keywordViewModel.uiState.vibrationMode,
                        alarmSiteList = keywordViewModel.uiState.siteList
                    )
                }
            }

            composable(
                route = NavScreen.KeywordAddInput.route
            ) {
                val keywordSearchingText = remember { mutableStateOf("") }

                keywordViewModel.getKeywordRecommendation()
                keywordViewModel.getRecentKeywordSearchList()

                KeywordAddInputScreen(
                    navController = navController,
                    errorMessage = stringResource(id = R.string.keyword_search_empty),
                    textFieldPlaceholder = stringResource(id = R.string.keyword_input),
                    tabDataList = mutableListOf("최근 검색", "추천 키워드"),
                    searchingText = keywordSearchingText,
                    recommendationList = keywordViewModel.uiState.recommendationList,
                    recentSearchList = keywordViewModel.uiState.recentSearchList,
                    searchList = keywordViewModel.uiState.searchList,
                    getSearchList = { keywordViewModel.getKeywordSearchList(keywordSearchingText.value) },
                    setRecentSearchList = {
                        keywordViewModel.setRecentKeywordSearchList(keywordViewModel.uiState.keyword)
                    },
                    setKeyword = { keywordViewModel.changeState(commend = "keyword", stringData = keywordSearchingText.value) }
                )
            }

            composable(
                route = NavScreen.KeywordSiteAddInput.route
            ) {
                val siteSearchingText = remember { mutableStateOf("") }

                keywordViewModel.getSiteRecommendation()
                keywordViewModel.getRecentSiteSearchList()

                KeywordAddInputScreen(
                    navController = navController,
                    errorMessage = stringResource(id = R.string.site_search_empty),
                    textFieldPlaceholder = stringResource(id = R.string.search_for_notifications_input),
                    tabDataList = mutableListOf("최근 검색", "추천 대상"),
                    searchingText = siteSearchingText,
                    recommendationList = keywordViewModel.uiState.recommendationList,
                    recentSearchList = keywordViewModel.uiState.recentSearchList,
                    searchList = keywordViewModel.uiState.searchList,
                    getSearchList = { keywordViewModel.getSiteSearchList(siteSearchingText.value) },
                    setRecentSearchList = { keywordViewModel.setRecentSiteSearchList(keywordViewModel.uiState.site) },
                    setKeyword = { keywordViewModel.changeState(commend = "site", stringData = siteSearchingText.value) }
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
                val deleteSite = remember { mutableStateOf("") }
                KeywordAddScreen(
                    screenName = stringResource(id = R.string.edit_keyword),
                    navController = navController,
                    deleteSite = deleteSite,
                    keywordViewModel = keywordViewModel,
                    pushOrEditKeyword = {
                        viewModel.editKeyword(
                            keyword = keywordViewModel.uiState.editKeyword,
                            alarmCycle = keywordViewModel.uiState.alarmCycle,
                            alarmMode = keywordViewModel.uiState.silentMode,
                            isImportant = keywordViewModel.uiState.isImportant,
                            name = keywordViewModel.uiState.keyword,
                            untilPressOkButton = keywordViewModel.uiState.untilPressOkButton,
                            vibrationMode = keywordViewModel.uiState.vibrationMode,
                            alarmSiteList = keywordViewModel.uiState.siteList
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