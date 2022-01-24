
package im.koala.bcsd.ui.main
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import im.koala.bcsd.ui.appbar.KoalaAppBar
import im.koala.bcsd.ui.chatroom.ChatRoomScreen
import im.koala.bcsd.ui.history.HistoryScreen
import im.koala.bcsd.ui.keyword.KeywordScreen
import im.koala.bcsd.ui.setting.SettingScreen
import androidx.compose.animation.Crossfade
@Composable
fun HomeTabScreen(
    viewModel: MainViewModel,
    tabStateHolder: HomeTabStateHolder,
    selectItem: (MainScreenBottomTab, Long) -> Unit
) {
    val selectedTab by viewModel.selectedTab
    val tabs = MainScreenBottomTab.values()

    Scaffold(
        backgroundColor = MaterialTheme.colors.primarySurface,
        topBar = { KoalaAppBar() },
        bottomBar = {

            BottomNavigation(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .navigationBarsHeight(56.dp)
            ) {

                tabs.forEach { tab ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = tab.drawableId),
                                contentDescription = null
                            )
                        },
                        label = { Text(text = stringResource(tab.title), color = MaterialTheme.colors.primary) },
                        selected = tab == selectedTab,
                        onClick = { viewModel.selectTab(tab) },
                        selectedContentColor = MaterialTheme.colors.secondary,
                        unselectedContentColor = MaterialTheme.colors.onBackground,
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        Crossfade(selectedTab) { destination ->
            when (destination) {
                MainScreenBottomTab.KEYWORD -> KeywordScreen(
                    modifier
                )
                MainScreenBottomTab.HISTORY -> HistoryScreen(
                    modifier
                )
                MainScreenBottomTab.CHATROOM -> ChatRoomScreen(
                    modifier
                )
                MainScreenBottomTab.SETTING -> SettingScreen(
                    modifier
                )
            }
        }
    }
}