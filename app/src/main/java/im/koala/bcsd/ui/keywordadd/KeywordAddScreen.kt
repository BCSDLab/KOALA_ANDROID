package im.koala.bcsd.ui.keywordadd

import android.widget.NumberPicker
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import im.koala.bcsd.R
import im.koala.bcsd.navigation.NavScreen
import im.koala.bcsd.ui.button.KoalaToggle
import im.koala.bcsd.ui.textfield.KoalaTextField
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.GrayAppBar
import im.koala.bcsd.ui.theme.KoalaTheme
import im.koala.bcsd.ui.theme.White
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.BlackBorder
import im.koala.bcsd.ui.theme.Yellow
import im.koala.bcsd.ui.theme.Gray

@ExperimentalMaterialApi
@Composable
fun KeywordAddScreen(
    screenName: String,
    navController: NavController,
    keywordViewModel: KeywordViewModel,
    deleteSite: MutableState<String>,
    pushOrEditKeyword: () -> Unit,
) {
    val isSiteError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val isKeywordError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val alarmCycleList = arrayOf("5분", "10분", "15분", "30분", "1시간", "2시간", "4시간", "6시간")
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        KeywordAddScreenTopBar(
            screenName = screenName,
            isSiteError = isSiteError,
            isKeywordError = isKeywordError,
            keywordViewModel = keywordViewModel,
            navController = navController,
            pushOrEditKeyword = { pushOrEditKeyword() },
        )

        Divider(
            color = GrayBorder,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )

        KeywordInputTextField(
            navController = navController,
            keywordViewModel = keywordViewModel,
            isKeywordError = isKeywordError,
        )

        SearchForNotificationsTextField(
            keywordViewModel = keywordViewModel,
            isSiteError = isSiteError,
            navController = navController,
            deleteSite = deleteSite,
        )

        NotificationsBox(
            keywordViewModel = keywordViewModel,
            selectAlarmCycle = keywordViewModel.uiState.alarmCycle,
            alarmCycleList = alarmCycleList,
        )
    }
}

@Composable
fun KeywordAddScreenTopBar(
    keywordViewModel: KeywordViewModel,
    screenName: String,
    isSiteError: MutableState<Boolean>,
    isKeywordError: MutableState<Boolean>,
    navController: NavController,
    pushOrEditKeyword: () -> Unit,
) {
    val context = LocalContext.current
    val errorMsg = stringResource(id = R.string.keyword_search_empty)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        IconButton(
            onClick = { navController.navigateUp() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_nav_back),
                contentDescription = "",
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(
            text = screenName,
            color = Black,
            fontSize = 16.sp
        )
        TextButton(
            onClick = {
                if (keywordViewModel.uiState.keyword.isEmpty()) Toast.makeText(
                    context,
                    errorMsg,
                    Toast.LENGTH_SHORT
                ).show()
                else {
                    isSiteError.value = keywordViewModel.uiState.siteList.isEmpty()
                    isKeywordError.value =
                        keywordViewModel.uiState.keyword in keywordViewModel.uiState.nameList
                    if (!isSiteError.value && !isKeywordError.value) {
                        pushOrEditKeyword()
                        navController.navigateUp()
                    }
                }
            },
        ) {
            Text(
                text = stringResource(id = R.string.find_password_done),
                fontSize = 14.sp,
                color = GrayAppBar
            )
        }
    }
}

@Composable
fun KeywordInputTextField(
    keywordViewModel: KeywordViewModel,
    navController: NavController,
    isKeywordError: MutableState<Boolean>,
) {
    KoalaTheme {
        val bottomPadding: Dp = if (isKeywordError.value) 4.dp else 16.dp
        Column(
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            Box(Modifier.fillMaxWidth()) {
                KoalaTextField(
                    value = keywordViewModel.uiState.keyword,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(horizontal = 16.dp),
                    onValueChange = { keywordViewModel.changeState("keyword", stringData = it) },
                    placeholder = { Text(text = stringResource(id = R.string.keyword_input)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_hashtag),
                            contentDescription = "",
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    isError = isKeywordError.value
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { navController.navigate(NavScreen.KeywordAddInput.route) }
                )
            }

            if (isKeywordError.value) {
                Text(
                    text = stringResource(id = R.string.error_search_for_keyword),
                    color = Yellow,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchForNotificationsTextField(
    keywordViewModel: KeywordViewModel,
    navController: NavController,
    isSiteError: MutableState<Boolean>,
    deleteSite: MutableState<String>,
) {
    KoalaTheme {
        val bottomPadding: Dp = if (isSiteError.value) 13.dp else 32.dp
        val deleteSign = remember { mutableStateOf(false) }

        Column(modifier = Modifier.padding(bottom = bottomPadding)) {
            Box(Modifier.fillMaxWidth()) {
                KoalaTextField(
                    value = keywordViewModel.uiState.site,
                    onValueChange = { keywordViewModel.changeState("site", stringData = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(horizontal = 16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.search_for_notifications)) },
                    isError = isSiteError.value,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "",
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { navController.navigate(NavScreen.KeywordSiteAddInput.route) }
                )
            }
            if (keywordViewModel.uiState.site.isNotEmpty()) {
                keywordViewModel.addAlarmSiteList(keywordViewModel.uiState.site)
                keywordViewModel.changeState("site")
            }

            if (isSiteError.value) {
                Text(
                    text = stringResource(id = R.string.error_search_for_alarm),
                    color = Yellow,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            SearchForNotificationsLazyColumn(
                keywordViewModel = keywordViewModel,
                deleteSign = deleteSign,
                deleteSite = deleteSite,
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchForNotificationsLazyColumn(
    keywordViewModel: KeywordViewModel,
    deleteSign: MutableState<Boolean>,
    deleteSite: MutableState<String>,
) {
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(keywordViewModel.uiState.siteList) {
            if (deleteSign.value) {
                keywordViewModel.deleteAlarmSite(deleteSite.value)
                deleteSign.value = false
            }
            SearchForNotificationsItem(text = it, deleteSign = deleteSign, deleteSite = deleteSite)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchForNotificationsItem(
    text: String,
    deleteSign: MutableState<Boolean>,
    deleteSite: MutableState<String>
) {
    ListItem(
        text = {
            Text(
                text = text
            )
        },
        trailing = {
            IconButton(
                onClick = {
                    deleteSite.value = text
                    deleteSign.value = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_x),
                    contentDescription = "",
                    modifier = Modifier.padding(2.dp)
                )
            }
        },
        modifier = Modifier
            .background(GrayBorder)
    )
}

@ExperimentalMaterialApi
@Composable
fun NotificationsBox(
    keywordViewModel: KeywordViewModel,
    selectAlarmCycle: Int,
    alarmCycleList: Array<String>,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .border(1.dp, GrayBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            TextButton(
                shape = RectangleShape,
                border = if (keywordViewModel.uiState.isImportant) BorderStroke(
                    1.dp,
                    BlackBorder
                ) else BorderStroke(1.dp, GrayBorder),
                modifier = Modifier.weight(0.5f),
                colors = if (keywordViewModel.uiState.isImportant) ButtonDefaults.buttonColors(Black) else ButtonDefaults.buttonColors(
                    White
                ),
                onClick = { keywordViewModel.changeState("isImportant") }
            ) {
                Text(
                    text = stringResource(id = R.string.keyword_important_alarm),
                    fontSize = 14.sp,
                    color = if (keywordViewModel.uiState.isImportant) White else GrayDisabled,
                    modifier = Modifier.padding(
                        start = 57.dp,
                        end = 56.dp,
                        top = 13.dp,
                        bottom = 14.dp
                    )
                )
            }
            TextButton(
                shape = RectangleShape,
                border = if (keywordViewModel.uiState.isImportant) BorderStroke(
                    1.dp,
                    GrayBorder
                ) else BorderStroke(1.dp, BlackBorder),
                modifier = Modifier.weight(0.5f),
                colors = if (keywordViewModel.uiState.isImportant) ButtonDefaults.buttonColors(White) else ButtonDefaults.buttonColors(
                    Black
                ),
                onClick = { keywordViewModel.changeState("isNotImportant") }
            ) {
                Text(
                    text = stringResource(id = R.string.keyword_general_alarm),
                    fontSize = 14.sp,
                    color = if (keywordViewModel.uiState.isImportant) GrayDisabled else White,
                    modifier = Modifier.padding(
                        start = 57.dp,
                        end = 56.dp,
                        top = 13.dp,
                        bottom = 14.dp
                    )
                )
            }
        }
        if (keywordViewModel.uiState.isImportant) ImportantNotificationBox(
            keywordViewModel,
            selectAlarmCycle,
            alarmCycleList,
        ) else GeneralNotificationBox(keywordViewModel)
    }
}

@ExperimentalMaterialApi
@Composable
fun ImportantNotificationBox(
    keywordViewModel: KeywordViewModel,
    selectAlarmCycle: Int,
    alarmCycleList: Array<String>,
) {
    val showDialog = remember { mutableStateOf(false) }
    Column {
        NotificationLine(
            keywordViewModel = keywordViewModel,
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_silent_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = keywordViewModel.uiState.silentMode,
            commend = "silentMode"
        )
        NotificationLine(
            keywordViewModel = keywordViewModel,
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_vibration_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = keywordViewModel.uiState.vibrationMode,
            commend = "vibrationMode"
        )
        NotificationLine(
            keywordViewModel = keywordViewModel,
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_until_check_button_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = keywordViewModel.uiState.untilPressOkButton,
            commend = "untilPressOkButton"
        )
        AlarmCycleLine(keywordViewModel, showDialog, selectAlarmCycle, alarmCycleList)
    }
}

@ExperimentalMaterialApi
@Composable
fun GeneralNotificationBox(keywordViewModel: KeywordViewModel) {
    Column {
        NotificationLine(
            keywordViewModel = keywordViewModel,
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_silent_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = keywordViewModel.uiState.silentMode,
            commend = "silentMode"
        )
        NotificationLine(
            keywordViewModel = keywordViewModel,
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_vibration_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = keywordViewModel.uiState.vibrationMode,
            commend = "vibrationMode"
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun NotificationLine(
    keywordViewModel: KeywordViewModel,
    text: @Composable () -> Unit,
    isChecked: Boolean,
    commend: String
) {
    val paddingStart: Dp by animateDpAsState(targetValue = if (isChecked) 24.dp else 1.dp)
    ListItem(
        text = text,
        trailing = {
            KoalaToggle(
                checked = isChecked,
                onCheckedChange = { keywordViewModel.changeState(commend) },
                modifier = Modifier
                    .width(48.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isChecked) Yellow else GrayDisabled),
                iconModifier = Modifier
                    .padding(start = paddingStart, top = 1.dp, bottom = 1.dp)
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(White),
            )
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun AlarmCycleLine(
    keywordViewModel: KeywordViewModel,
    showDialog: MutableState<Boolean>,
    selectAlarmCycle: Int,
    alarmCycleList: Array<String>
) {
    if (showDialog.value) AlarmCycleDialog(showDialog, keywordViewModel, alarmCycleList)
    ListItem(
        text = {
            Text(
                text = stringResource(id = R.string.keyword_alarm_cycle),
                color = Black,
                fontSize = 14.sp
            )
        },
        trailing = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = alarmCycleList[selectAlarmCycle],
                    color = Black,
                    fontSize = 14.sp
                )
                IconButton(
                    onClick = { showDialog.value = true },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(27.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        contentDescription = "",
                    )
                }
            }
        }
    )
}

@Composable
fun AlarmCycleDialog(
    openDialog: MutableState<Boolean>,
    keywordViewModel: KeywordViewModel,
    alarmCycleList: Array<String>
) {
    var temp = 0
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = stringResource(id = R.string.keyword_alarm_cycle_choice),
                    color = Black
                )
            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AndroidView(factory = { context ->
                        NumberPicker(context).apply {
                            setOnValueChangedListener { numberPicker, i, i2 ->
                                temp = i2
                            }
                            minValue = 0
                            maxValue = 7
                            displayedValues = alarmCycleList
                        }
                    })
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        colors = ButtonDefaults.buttonColors(White),
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.keyword_close),
                            color = Gray
                        )
                    }
                    TextButton(
                        colors = ButtonDefaults.buttonColors(White),
                        onClick = {
                            keywordViewModel.changeState("alarmCycle", intData = temp)
                            openDialog.value = false
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.find_password_done),
                            color = Yellow
                        )
                    }
                }
            }
        )
    }
}