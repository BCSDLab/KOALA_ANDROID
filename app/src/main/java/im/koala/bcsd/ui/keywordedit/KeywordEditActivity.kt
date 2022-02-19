package im.koala.bcsd.ui.keywordedit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import im.koala.bcsd.R
import im.koala.bcsd.ui.keywordadd.KeywordAddScreen
import im.koala.bcsd.ui.keywordadd.KeywordViewModel
import im.koala.bcsd.ui.main.MainViewModel
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.data.api.response.keywordadd.KeywordAddResponseUi

@ExperimentalMaterialApi
class KeywordEditActivity : ComponentActivity() {
    private val keywordViewModel: KeywordViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoalaTheme {
                val keywordSearchText = remember { mutableStateOf("") }
                val alarmDistinction: MutableState<Boolean> = remember { mutableStateOf(true) }
                val selectAlarmCycle: MutableState<Int> = remember { mutableStateOf(0) }
                val alarmCheckedList: List<MutableState<Boolean>> = arrayListOf(
                    remember { mutableStateOf(true) },
                    remember { mutableStateOf(true) },
                    remember { mutableStateOf(true) }
                )
                val alarmSiteList by keywordViewModel.alarmSiteList.observeAsState(listOf(""))
                val uiState by keywordViewModel.uiState.observeAsState(KeywordAddResponseUi())
                val deleteSite = remember { mutableStateOf("") }
                val keywordNameList by keywordViewModel.keywordNameList.observeAsState(listOf(""))
                val alarmSiteText = remember { mutableStateOf("") }

                KeywordAddScreen(
                    screenName = stringResource(id = R.string.edit_keyword),
                    navController = NavController(this),
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
                    pushKeyword = {
                        mainViewModel.editKeyword(
                            keyword = keywordSearchText.value,
                            alarmCycle = selectAlarmCycle.value,
                            alarmMode = alarmCheckedList[0].value,
                            isImportant = alarmDistinction.value,
                            name = keywordSearchText.value,
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