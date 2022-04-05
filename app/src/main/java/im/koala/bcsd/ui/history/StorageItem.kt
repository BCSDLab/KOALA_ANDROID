package im.koala.bcsd.ui.history

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaCheckBox
import im.koala.bcsd.ui.theme.*
import im.koala.bcsd.util.compose.LocalizedMessage
import im.koala.domain.entity.history.Memo
import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.entity.keyword.Site
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun StorageItem(
    modifier: Modifier,
    scrapNotice: ScrapNotice,
    onCheckedChange: (checked: Boolean) -> Unit,
    onClickFinishButton: (ScrapNotice, String) -> Unit
) {
    val isEditingMemo = remember { mutableStateOf(false) }
    val editingMemo = remember {
        mutableStateOf(
            if (scrapNotice.memo == null) ""
            else scrapNotice.memo!!.memo
        )
    }
    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val swipeSize = with(LocalDensity.current) { 176.dp.toPx() }
    val swipeAnchors = mapOf(0f to 0, swipeSize to 1)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .swipeable(
                state = swipeAbleState,
                anchors = swipeAnchors,
                orientation = Orientation.Horizontal,
                reverseDirection = true
            )
    ) {
        Column(
            modifier = modifier
                .padding(start = 18.dp, end = 16.dp, top = 16.dp)
                .offset {
                    if (isEditingMemo.value) IntOffset(0, 0)
                    else IntOffset(swipeAbleState.offset.value.roundToInt() * (-1), 0)
                }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                KoalaCheckBox(
                    checked = scrapNotice.isChecked,
                    onCheckedChange = onCheckedChange,
                    modifier = modifier
                        .width(16.dp)
                        .height(21.dp)
                        .padding(top = 3.dp, bottom = 2.dp)
                )
                Text(
                    text = scrapNotice.site.LocalizedMessage(),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = modifier
                        .padding(start = 8.dp)
                )
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = scrapNotice.createdAt,
                        style = TextStyle(
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Normal,
                            fontSize = 11.sp
                        )
                    )
                }
            }
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 29.dp, top = 8.dp),
                text = scrapNotice.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary
            )
            if (isEditingMemo.value) {
                MemoTextField(
                    modifier = modifier
                        .padding(top = 8.dp, start = 24.dp, end = 8.dp)
                        .fillMaxWidth(),
                    textFieldValueState = editingMemo,
                    onClickFinishButton = { memo ->
                        onClickFinishButton(scrapNotice, memo)
                        isEditingMemo.value = false
                    }
                )
            } else {
                scrapNotice.memo?.let {
                    Row(
                        modifier = modifier
                            .padding(start = 24.dp, end = 29.dp, top = 8.dp)
                    ) {
                        Canvas(
                            modifier = modifier
                                .padding(top = 5.dp, end = 4.dp)
                                .width(8.dp)
                                .height(8.dp)
                        ) {
                            drawCircle(
                                color = Yellow,
                                center = Offset(x = size.width / 2, y = size.height / 2),
                                radius = size.minDimension / 2
                            )
                        }
                        Text(
                            text = it.memo,
                            style = TextStyle(
                                color = MaterialTheme.colors.onBackground,
                                fontWeight = FontWeight.Normal,
                                fontSize = 11.sp
                            ),
                            modifier = modifier
                                .fillMaxWidth(fraction = 0.75f)
                        )
                    }
                }
            }
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, top = 15.dp),
                color = GrayBorder
            )
        }
        Row(
            modifier = modifier
                .align(alignment = Alignment.CenterEnd)
                .width(
                    swipeAbleState.offset.value.let {
                        if (isEditingMemo.value) 0.dp
                        else {
                            if (it > 176) 176.dp
                            else it.dp
                        }
                    }
                )
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .background(color = Yellow)
                    .clickable { isEditingMemo.value = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil),
                    contentDescription = "",
                    tint = White,
                    modifier = modifier
                        .width(24.dp)
                        .height(24.dp)
                )
                Text(
                    text = stringResource(id = R.string.history_memo_edit),
                    style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .background(color = Black)
                    .clickable { isEditingMemo.value = true }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pencil_alt),
                    contentDescription = "",
                    tint = White,
                    modifier = modifier
                        .width(24.dp)
                        .height(24.dp)
                )
                Text(
                    text = stringResource(id = R.string.history_memo_add),
                    style = TextStyle(
                        color = White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun MemoTextField(
    textFieldValueState: MutableState<String>,
    onClickFinishButton: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = GrayBorder
            )
    ) {
        BasicTextField(
            value = textFieldValueState.value,
            onValueChange = {
                if (it.length <= 100) textFieldValueState.value = it
            },
            textStyle = TextStyle(
                fontSize = 11.sp,
                color = Black,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .background(color = GrayBorder)
                .padding(start = 8.dp, end = 8.dp, top = 3.dp, bottom = 5.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = textFieldValueState.value.length.toString(),
                    style = MaterialTheme.typography.body2,
                    color = if (textFieldValueState.value.length == 100) Yellow
                    else Gray
                )
                Text(
                    text = stringResource(id = R.string.history_memo_character_limit),
                    style = MaterialTheme.typography.body2,
                    color = Gray
                )
            }
            Text(
                text = stringResource(id = R.string.history_memo_edit_finish),
                style = MaterialTheme.typography.body2,
                color = Gray,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { onClickFinishButton(textFieldValueState.value) }
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun previewStorageItem() {
    KoalaTheme {
        StorageItem(
            modifier = Modifier,
            scrapNotice = ScrapNotice(
                id = 1,
                crawlingAt = "00:00",
                createdAt = "00:00",
                memo = Memo(
                    userScrapedId = 1,
                    updatedAt = "00:00",
                    createdAt = "00:00",
                    memo = "메모 테스트 매우 길게 이것은 아주 긴 메모의 테스트를 위한 것 이 메모는 길어야 한다. 테스트를 위해서 "
                ),
                site = Site.Portal,
                title = "테스트 제목길고긴 테스트 제목 긴 제목을 위한 테스트 제목 ㅁㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄻㄴㅇㄹㄴㅁㅇㄹㄴㅁㅇㄹㄴㅁ",
                url = "테스트 url",
                userScrapedId = 1,
                isChecked = false
            ),
            onCheckedChange = {},
            onClickFinishButton = { scrapNotice, memo ->
            }
        )
    }
}

@Preview
@Composable
fun previewMemoTextField() {
    KoalaTheme {
        val state = remember { mutableStateOf("") }
        MemoTextField(textFieldValueState = state, onClickFinishButton = {})
    }
}