package im.koala.bcsd.ui.chatroom.compose

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.R
import im.koala.bcsd.ui.chatroom.chat.Chat
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun ChatNumberOfPeople(
    modifier: Modifier = Modifier,
    numberOfPeople: Int
) {
    Row(
        modifier = modifier
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.secondary) {
            Icon(
                painter = painterResource(id = R.drawable.ic_users),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = numberOfPeople.toString(),
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
fun ChatDate(
    formattedDate: String
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = GrayBorder
        )

        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .border(
                    width = 1.dp,
                    color = GrayBorder
                )
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = formattedDate,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )
        }

    }
}

@Composable
fun OpponentChatMessage(
    chat: Chat.OpponentChat,
    isSelected: Boolean = false,
    onItemClick: (Int) -> Unit,
    onReportClick: (Int) -> Unit
) {
    val backgroundColor = animateColorAsState(
        targetValue = if (isSelected) GrayBorder else MaterialTheme.colors.background
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(backgroundColor.value)
            .clickable(
                onClick = {
                    onItemClick(chat.chatId)
                },
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 60.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                modifier = Modifier.padding(top = 4.dp, end = 16.dp),
                painter = chat.profileImage,
                contentDescription = ""
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.alignByBaseline(),
                        text = chat.nickname,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.secondary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier
                            .alignByBaseline()
                            .padding(horizontal = 4.dp),
                        text = chat.formattedTime,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    text = chat.message,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondary
                )
            }
        }

        Crossfade(targetState = isSelected) {
            if (it) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .defaultMinSize(minHeight = 60.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                0.0f to Color.Transparent,
                                0.5f to Color.Transparent,
                                0.75f to GrayBorder
                            )
                        ),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                onReportClick(chat.chatId)
                            }
                            .padding(vertical = 16.dp, horizontal = 24.dp),
                        text = stringResource(id = R.string.report),
                        style = MaterialTheme.typography.caption,
                        fontSize = 10.sp,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }

        }
    }


}

@Composable
@Preview
fun ChatNumberOfPeoplePreview() {
    KoalaTheme {
        Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
            ChatNumberOfPeople(numberOfPeople = 24)
        }
    }
}

@Composable
@Preview
fun ChatDatePreview() {
    KoalaTheme {
        Box(modifier = Modifier.background(MaterialTheme.colors.background)) {
            ChatDate(formattedDate = "8/15 (일)")
        }
    }
}

@Composable
@Preview
fun ChatMessagePreview() {
    KoalaTheme {
        Column(modifier = Modifier.background(MaterialTheme.colors.background)) {

            val selectedState1 = rememberSaveable { mutableStateOf(false) }
            val selectedState2 = rememberSaveable { mutableStateOf(false) }

            OpponentChatMessage(
                chat = Chat.OpponentChat(
                    chatId = 100,
                    profileImage = painterResource(id = R.drawable.ic_tutorial_profile_red),
                    nickname = "쭈구리",
                    formattedTime = "18:30",
                    message = "안녕안녕요~~ 반가워요!"
                ),
                isSelected = selectedState1.value,
                onItemClick = { selectedState1.value = !selectedState1.value },
                onReportClick = {}
            )

            OpponentChatMessage(
                chat = Chat.OpponentChat(
                    chatId = 101,
                    profileImage = painterResource(id = R.drawable.ic_tutorial_profile_red),
                    nickname = "쭈구리",
                    formattedTime = "18:30",
                    message = "안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!안녕안녕요~~ 반가워요!"
                ),
                onItemClick = { selectedState2.value = !selectedState2.value },
                isSelected = selectedState2.value,
                onReportClick = {}
            )
        }
    }
}