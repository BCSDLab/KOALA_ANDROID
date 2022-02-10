package im.koala.bcsd.ui.keyword

import android.util.Log
import android.widget.NumberPicker
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
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
import im.koala.bcsd.ui.main.MainViewModel
import im.koala.bcsd.ui.textfield.KoalaTextField
import im.koala.bcsd.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun KeywordAddScreen(
    screenName:String,
    navController: NavController,
    selectAlarmCycle: MutableState<Int>,
    alarmDistinction:MutableState<Boolean>,
    keywordSearchText:MutableState<String>,
    alarmSiteText:MutableState<String>,
    deleteSite:MutableState<String>,
    alarmSiteList:List<String>,
    alarmCheckedList: List<MutableState<Boolean>>,
    keywordNameList: List<String>,
    addAlarmSiteList: () -> Unit,
    deleteAlarmSite: () -> Unit,
    deleteAllAlarmSiteList: () -> Unit,
    pushKeyword: () -> Unit,
){
    val isSiteError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val isKeywordError: MutableState<Boolean> = remember { mutableStateOf(false) }
    val alarmCycleList = arrayOf( "5분", "10분", "15분", "30분", "1시간", "2시간", "4시간", "6시간")

    Column(modifier = Modifier.fillMaxSize()) {
        KeywordAddScreenTopBar(
            screenName = screenName,
            isSiteError = isSiteError,
            isKeywordError = isKeywordError,
            keywordSearchText = keywordSearchText,
            alarmSiteList = alarmSiteList,
            navController = navController,
            keywordNameList = keywordNameList,
            pushKeyword = { pushKeyword() },
            deleteAllSiteList = { deleteAllAlarmSiteList() },
        )

        Divider(
            color = GrayBorder,
            modifier = Modifier
                .padding(bottom = 24.dp)
        )

        KeywordInputTextField(
            navController = navController,
            keywordText = keywordSearchText,
            isKeywordError = isKeywordError
        )

        SearchForNotificationsTextField(
            text = alarmSiteText,
            isSiteError = isSiteError,
            navController = navController,
            alarmSiteList = alarmSiteList,
            deleteSite = deleteSite,
            deleteAlarmSite = { deleteAlarmSite() },
            addAlarmSiteList = { addAlarmSiteList() }
        )

        NotificationsBox(
            notificationDistinction = alarmDistinction,
            selectAlarmCycle = selectAlarmCycle,
            alarmCycleList = alarmCycleList,
            alarmCheckedList = alarmCheckedList,
        )
    }
}

@Composable
fun KeywordAddScreenTopBar(
    screenName:String,
    isSiteError: MutableState<Boolean>,
    isKeywordError: MutableState<Boolean>,
    keywordSearchText:MutableState<String>,
    alarmSiteList:List<String>,
    navController: NavController,
    keywordNameList:List<String>,
    pushKeyword: () ->Unit,
    deleteAllSiteList: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        IconButton(
            onClick = {
                deleteAllSiteList()
                keywordSearchText.value = ""
                navController.navigateUp()
            }
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
                isSiteError.value = alarmSiteList.isEmpty()
                isKeywordError.value = keywordSearchText.value in keywordNameList || keywordSearchText.value.isEmpty()
                if(!isSiteError.value && !isKeywordError.value) {
                    pushKeyword()
                    deleteAllSiteList()
                    keywordSearchText.value = ""
                    navController.navigateUp()
                }
                      },
        ) {
            Text(
                text = stringResource(id = R.string.find_password_done),
                fontSize = 14.sp,
                color = GrayFontColor
            )
        }
    }
}

@Composable
fun KeywordInputTextField(
    navController: NavController,
    isKeywordError: MutableState<Boolean>,
    keywordText:MutableState<String>
) {
    KoalaTheme {
        val bottomPadding: Dp = if (isKeywordError.value) 4.dp else 16.dp
        Column (
            modifier = Modifier.padding(bottom = bottomPadding)
            ){
            Box(Modifier.fillMaxWidth()){
                KoalaTextField(
                    value = keywordText.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(horizontal = 16.dp),
                    onValueChange = { keywordText.value = it },
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
                Box(modifier = Modifier
                    .matchParentSize()
                    .clickable { navController.navigate(NavScreen.KeywordAddInput.route) })
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
    navController: NavController,
    text: MutableState<String>,
    isSiteError: MutableState<Boolean>,
    alarmSiteList:List<String>,
    deleteSite:MutableState<String>,
    addAlarmSiteList: () -> Unit,
    deleteAlarmSite: () -> Unit
) {
    KoalaTheme {
        val bottomPadding: Dp = if (isSiteError.value) 13.dp else 32.dp
        val deleteSign = remember{ mutableStateOf(false) }

        Column(modifier = Modifier.padding(bottom = bottomPadding)) {
            Box(Modifier.fillMaxWidth()){
                KoalaTextField(
                    value = text.value, onValueChange = {
                        text.value = it
                    },
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
                Box(modifier = Modifier
                    .matchParentSize()
                    .clickable { navController.navigate(NavScreen.KeywordSiteAddInput.route) })
            }
            if(text.value.isNotEmpty()){
                addAlarmSiteList()
                text.value = ""
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
                notificationSiteList = alarmSiteList,
                deleteSign = deleteSign,
                deleteSite = deleteSite,
                deleteAlarmSite = { deleteAlarmSite() },
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchForNotificationsLazyColumn(
    notificationSiteList:List<String>,
    deleteSign:MutableState<Boolean>,
    deleteSite:MutableState<String>,
    deleteAlarmSite: () -> Unit,
){
    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ){
        items(notificationSiteList){
            if(deleteSign.value){
                deleteAlarmSite()
                deleteSign.value = false
            }
            SearchForNotificationsItem( text = it, deleteSign = deleteSign,deleteSite = deleteSite )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SearchForNotificationsItem(text:String,deleteSign: MutableState<Boolean>,deleteSite:MutableState<String>) {
    ListItem(
        text = {
               Text(
                   text = text
               )
        },
        trailing = {
            IconButton(onClick = {
                deleteSite.value = text
                deleteSign.value = true
            })
            {
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
    notificationDistinction: MutableState<Boolean>,
    selectAlarmCycle: MutableState<Int>,
    alarmCycleList: Array<String>,
    alarmCheckedList: List<MutableState<Boolean>>,
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
                border = if (notificationDistinction.value) BorderStroke(1.dp, BlackBorder) else BorderStroke(1.dp, GrayBorder),
                modifier = Modifier.weight(0.5f),
                colors = if (notificationDistinction.value) ButtonDefaults.buttonColors(Black) else ButtonDefaults.buttonColors(White),
                onClick = { notificationDistinction.value = true }
            ) {
                Text(
                    text = stringResource(id = R.string.keyword_important_alarm),
                    fontSize = 14.sp,
                    color = if (notificationDistinction.value) White else GrayDisabled,
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
                border = if (notificationDistinction.value) BorderStroke(
                    1.dp,
                    GrayBorder
                ) else BorderStroke(1.dp, BlackBorder),
                modifier = Modifier.weight(0.5f),
                colors = if (notificationDistinction.value) ButtonDefaults.buttonColors(White) else ButtonDefaults.buttonColors(Black),
                onClick = { notificationDistinction.value = false }
            ) {
                Text(
                    text = stringResource(id = R.string.keyword_general_alarm),
                    fontSize = 14.sp,
                    color = if (notificationDistinction.value) GrayDisabled else White,
                    modifier = Modifier.padding(
                        start = 57.dp,
                        end = 56.dp,
                        top = 13.dp,
                        bottom = 14.dp
                    )
                )
            }
        }
        if (notificationDistinction.value) ImportantNotificationBox(
            selectAlarmCycle,
            alarmCycleList,
            alarmCheckedList
        ) else GeneralNotificationBox(alarmCheckedList)
    }
}

@ExperimentalMaterialApi
@Composable
fun ImportantNotificationBox(
    selectAlarmCycle: MutableState<Int>,
    alarmCycleList: Array<String>,
    isCheckedList: List<MutableState<Boolean>>
) {
    val showDialog = remember { mutableStateOf(false) }
    Column {
        NotificationLine(
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_silent_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = isCheckedList[0]
        )
        NotificationLine(
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_vibration_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = isCheckedList[1]
        )
        NotificationLine(
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_until_check_button_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = isCheckedList[2]
        )
        AlarmCycleLine(showDialog, selectAlarmCycle, alarmCycleList)

    }
}

@ExperimentalMaterialApi
@Composable
fun GeneralNotificationBox(alarmCheckedList: List<MutableState<Boolean>>) {
    Column {
        NotificationLine(
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_silent_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = alarmCheckedList[0]
        )
        NotificationLine(
            text = {
                Text(
                    text = stringResource(id = R.string.keyword_vibration_mode_alarm),
                    color = Black,
                    fontSize = 14.sp
                )
            },
            isChecked = alarmCheckedList[1]
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun NotificationLine(text: @Composable () -> Unit, isChecked: MutableState<Boolean>) {
    val paddingStart: Dp by animateDpAsState(targetValue = if (isChecked.value) 24.dp else 1.dp)
    ListItem(
        text = text,
        trailing = {
            KoalaToggle(
                checked = isChecked.value,
                onCheckedChange = { isChecked.value = !isChecked.value },
                modifier = Modifier
                    .width(48.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(50))
                    .background(if (isChecked.value) Yellow else GrayDisabled),
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
    showDialog: MutableState<Boolean>,
    selectAlarmCycle: MutableState<Int>,
    alarmCycleList: Array<String>
) {
    if (showDialog.value) AlarmCycleDialog(showDialog, selectAlarmCycle, alarmCycleList)
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
                    text = alarmCycleList[selectAlarmCycle.value],
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
    selectAlarmCycle: MutableState<Int>,
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
                        }) {
                        Text(
                            text = stringResource(id = R.string.keyword_close),
                            color = Gray
                        )
                    }
                    TextButton(
                        colors = ButtonDefaults.buttonColors(White),
                        onClick = {
                            selectAlarmCycle.value = temp
                            Log.d("asdfsdf", selectAlarmCycle.value.toString() + ": ad")
                            openDialog.value = false
                        }) {
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

//@ExperimentalMaterialApi
//@Preview(name = "키워드 레이아웃", showBackground = true)
//@Composable
//fun KeywordScreenPreview() {
//    val text = remember { mutableStateOf("") }
//    val navController = rememberNavController()
//    val text2= remember { mutableStateOf("") }
//
//    KeywordAddScreen(navController,text,text2)
//}

//@Preview(name = "상단 내용", showBackground = true)
//@Composable
//fun KeywordAddScreenTopBarPreview() {
//    val isError: MutableState<Boolean> = remember {mutableStateOf(false)}
//    val notificationDistinction: MutableState<Boolean> = remember{mutableStateOf(true)}
//    val text = remember { mutableStateOf("") }
//    KeywordAddScreenTopBar(text, isError)
//}
//
//@Preview(name = "키워드 검색", showBackground = true)
//@Composable
//fun KeywordInputTextFieldPreview() {
//    val navController = rememberNavController()
//    KeywordInputTextField(navController)
//}
//
//@ExperimentalMaterialApi
//@Preview(name = "알림받을 대상 검색", showBackground = true)
//@Composable
//fun SearchForNotificationsTextFieldPreview() {
//    val isError1: MutableState<Boolean> = remember {mutableStateOf(false)}
//    val isError2: MutableState<Boolean> = remember {mutableStateOf(true)}
//
//    val text = remember { mutableStateOf("") }
//    Column() {
//        SearchForNotificationsTextField(text, isError2)
//        Spacer(modifier = Modifier.size(10.dp))
//        SearchForNotificationsTextField(text, isError1)
//    }
//}
//
//@ExperimentalMaterialApi
//@Preview(name = "중요알림 라인", showBackground = true)
//@Composable
//fun NotificationLinePreview() {
//    val showDialog = remember {mutableStateOf(false)}
//    val selectAlarmCycle = remember{mutableStateOf(0)}
//    val alarmCycleList = arrayOf<String>(
//        "10분",
//        "20분",
//        "30분",
//        "40분",
//        "50분",
//    )
//    val importantIsCheckedList: List<MutableState<Boolean>> = arrayListOf(
//        remember { mutableStateOf(true) },
//        remember { mutableStateOf(true) },
//        remember { mutableStateOf(true) }
//    )
//    Column {
//        NotificationLine(
//            text = {
//                Text(
//                    text = "무음모드 알림",
//                )
//            },
//            isChecked = importantIsCheckedList[0]
//        )
//        NotificationLine(
//            text = {
//                Text(
//                    text = "진동 알림",
//                )
//            },
//            isChecked = importantIsCheckedList[1]
//        )
//        NotificationLine(
//            text = {
//                Text(
//                    text = "확인버튼누를 때 까지 알림",
//                )
//            },
//            isChecked = importantIsCheckedList[2]
//        )
//        AlarmCycleLine(showDialog, selectAlarmCycle, alarmCycleList)
//    }
//}
//
//@ExperimentalMaterialApi
//@Preview(name = "일반알림 라인", showBackground = true)
//@Composable
//fun GeneralNotificationLinePreview() {
//    val generalIsCheckedList: List<MutableState<Boolean>> = arrayListOf(
//        remember { mutableStateOf(true) },
//        remember { mutableStateOf(true) }
//    )
//    Column {
//        NotificationLine(
//            text = {
//                Text(
//                    text = "무음모드 알림",
//                    fontSize = 14.sp
//                )
//            },
//        isChecked = generalIsCheckedList[0]
//        )
//        NotificationLine(
//            text = {
//                Text(
//                    text = "진동 알림",
//                    fontSize = 14.sp
//                )
//            },
//            isChecked = generalIsCheckedList[1]
//        )
//    }
//}
//
//@ExperimentalMaterialApi
//@Preview(name = "알림박스", showBackground = true)
//@Composable
//fun NotificationsBoxPreview() {
//    val notificationDistinction = remember{mutableStateOf(true)}
//    val selectAlarmCycle: MutableState<Int> = remember{mutableStateOf(0)}
//    val alarmCycleList = arrayOf<String>(
//        "5분",
//        "10분",
//        "15분",
//        "30분",
//        "1시간",
//        "2시간",
//        "4시간",
//        "6시간",
//    )
//    val importantIsCheckedList: List<MutableState<Boolean>> = arrayListOf(
//        remember { mutableStateOf(true) },
//        remember { mutableStateOf(true) },
//        remember { mutableStateOf(true) }
//    )
//    val generalIsCheckedList: List<MutableState<Boolean>> = arrayListOf(
//        remember { mutableStateOf(true) },
//        remember { mutableStateOf(true) }
//    )
//    NotificationsBox(notificationDistinction, selectAlarmCycle, alarmCycleList,importantIsCheckedList,generalIsCheckedList)
//}
//
//@ExperimentalMaterialApi
//@Preview(name = "알림받을 대상 아이템", showBackground = true)
//@Composable
//fun SearchForNotificationsItemPreview() {
//    SearchForNotificationsItem(
//        text = {
//            Text(
//                text = "인스타그램",
//                fontSize = 12.sp
//            )
//        })
//}
//
//@ExperimentalMaterialApi
//@Preview(name = "알림받을 대상 LAZY COLUMN", showBackground = true)
//@Composable
//fun SearchForNotificationsLazyColumnPreview(){
//    SearchForNotificationsLazyColumn()
//}