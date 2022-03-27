package im.koala.bcsd.ui.chatroom.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.chatroom.chat.Chat
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun ChatScreen(
    chats : List<Chat>,
    numberOfPeople: Int,
    onBack: () -> Unit,
    onAttachImageButtonClicked: () -> Unit,
    onSendButtonClicked: () -> Unit,
    onChatItemClick: (Int) -> Unit,
    onChatReportItemClick: (Int) -> Unit
) {
    BackHandler(onBack = onBack)

    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        Scaffold(
            topBar = {
                KoalaTextAppBar(title = "채팅") {
                    ChatNumberOfPeople(
                        modifier = Modifier.padding(end = 8.dp),
                        numberOfPeople = numberOfPeople)
                }
            },
            bottomBar = {
                ChatBar(
                    modifier = Modifier.navigationBarsWithImePadding(),
                    onAttachImageButtonClicked = onAttachImageButtonClicked,
                    onSendButtonClicked = onSendButtonClicked
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(it).fillMaxSize(),
                reverseLayout = true
            ) {
                items(chats.size) { chatIndex ->
                    with(chats[chatIndex]) {
                        when (this) {
                            is Chat.DateChat -> ChatDate(formattedDate = formattedDate)
                            is Chat.MyChat -> TODO()
                            is Chat.OpponentChat -> OpponentChatMessage(
                                chat = this,
                                onItemClick = onChatItemClick,
                                onReportClick = onChatReportItemClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBar(
    modifier: Modifier = Modifier,
    onAttachImageButtonClicked: () -> Unit,
    onSendButtonClicked: () -> Unit,
) {
    val chatFocusRequester = remember { FocusRequester() }
    val chatText = rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.secondary
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .defaultMinSize(minHeight = 56.dp)
                .padding(8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.secondary) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = onAttachImageButtonClicked)
                            .padding(8.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_photograph),
                        contentDescription = stringResource(R.string.string_chat_attach_image)
                    )
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = { })
                            .padding(8.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_text_style),
                        contentDescription = stringResource(R.string.string_chat_set_typo)
                    )
                }
                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clickable(onClick = onSendButtonClicked)
                        .padding(16.dp)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.ic_paper_airplane),
                    contentDescription = stringResource(R.string.string_chat_send_chat)
                )

                Row(
                    modifier = Modifier
                        .padding(
                            start = 96.dp,
                            end = 48.dp
                        )
                        .padding(vertical = 8.dp)
                        .fillMaxSize()
                        .clickable { chatFocusRequester.requestFocus() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester = chatFocusRequester),
                        value = chatText.value,
                        onValueChange = { chatText.value = it },
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ChatBarPreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ChatBar(
                onAttachImageButtonClicked = {},
                onSendButtonClicked = {}
            )
        }
    }
}

@Composable
@Preview
fun ChatScreenPreview() {
    KoalaTheme {
        ChatScreen(
            chats = (listOf(Chat.DateChat("8/15(일)")) + (1..4).map {
                Chat.OpponentChat(
                    it,
                    painterResource(id = R.drawable.ic_tutorial_profile_red),
                    "$it${it + 1}",
                    "18:${String.format("%2d", it)}",
                    "asdfasdf$it"
                )
            } + listOf(Chat.DateChat("8/16(월)")) + (6..9).map {
                Chat.OpponentChat(
                    it,
                    painterResource(id = R.drawable.ic_tutorial_profile_red),
                    "$it${it + 1}",
                    "18:${String.format("%02d", it)}",
                    "asdfasdf$it"
                )
            }).reversed(),
            numberOfPeople = 24,
            onBack = { },
            onSendButtonClicked = {},
            onAttachImageButtonClicked = {},
            onChatItemClick = {},
            onChatReportItemClick = {}
        )
    }
}
