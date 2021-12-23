package im.koala.bcsd.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import im.koala.bcsd.R
import im.koala.bcsd.navigation.NavScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val tabStateHolder = HomeTabStateHolder(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState()
    )
    ProvideWindowInsets {
        NavHost(navController = navController, startDestination = NavScreen.Keyword.route ) {
            composable(
                route = NavScreen.Keyword.route,
                arguments = emptyList()
            ) {
                HomeTabScreen(
                    viewModel = hiltViewModel(),
                    tabStateHolder = tabStateHolder,
                    selectItem = {tab, index ->
                        when (tab) {
                            MainScreenBottomTab.KEYWORD -> navController.navigate("${NavScreen.KeywordDetails.route}/$index")
                            MainScreenBottomTab.HISTORY -> navController.navigate("${NavScreen.HistoryDetails.route}/$index")
                            MainScreenBottomTab.CHATROOM -> navController.navigate("${NavScreen.ChatRoomDetails.route}/$index")
                            MainScreenBottomTab.SETTING -> navController.navigate("${NavScreen.SettingDetails.route}/$index")
                        }
                    }
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