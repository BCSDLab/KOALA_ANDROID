package im.koala.bcsd.ui.chatroom.chat

import androidx.compose.ui.graphics.painter.Painter

sealed class Chat {
    data class DateChat(
        val formattedDate: String
    ) : Chat()

    data class OpponentChat(
        val chatId: Int,
        val profileImage: Painter,
        val nickname: String,
        val formattedTime: String,
        val message: String
    ) : Chat()

    data class MyChat(
        val chatId: Int,
        val profileImage: Painter,
        val nickname: String,
        val formattedTime: String,
        val message: String
    ) : Chat()

    companion object {
        const val DATE_FORMAT_DATE_CHAT = "M/dd(E)"
        const val DATE_FORMAT_CHAT = "hh:mm"
    }
}
