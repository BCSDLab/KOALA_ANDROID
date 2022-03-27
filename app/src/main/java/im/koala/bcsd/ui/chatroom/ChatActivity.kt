package im.koala.bcsd.ui.chatroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.painterResource
import im.koala.bcsd.R
import im.koala.bcsd.ui.chatroom.chat.Chat
import im.koala.bcsd.ui.chatroom.compose.ChatScreen
import im.koala.bcsd.ui.theme.KoalaTheme

class ChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
                    onBack = {},
                    onSendButtonClicked = {},
                    onAttachImageButtonClicked = {},
                    onChatItemClick = {},
                    onChatReportItemClick = {}
                )
            }
        }
    }
}