package im.koala.bcsd.ui.chatroom.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaButton
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    numberOfPeople: Int,
    bestKeyword: String,
    onEnterClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .align(Alignment.Center),
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.string_chat_welcome_descripiton_best_keyword),
                style = MaterialTheme.typography.subtitle2
            )

            Box(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.string_chat_welcome_best_keyword, bestKeyword),
                    style = MaterialTheme.typography.subtitle1
                )

                ChatNumberOfPeople(numberOfPeople = numberOfPeople)
            }

            Box(
                Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(vertical = 24.dp),
                    painter = painterResource(id = R.drawable.img_chat),
                    contentDescription = ""
                )
            }

            Text(
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp),
                text = stringResource(R.string.string_chat_welcome_caution),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onBackground
            )

            Spacer(
                modifier = Modifier
                    .height(72.dp)
                    .fillMaxWidth()
            )
        }

        KoalaButton(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 22.dp)
                .fillMaxWidth()
                .height(48.dp),
            onClick = onEnterClicked
        ) {
            Text(text = stringResource(R.string.string_chat_welcome_enter_chat))
        }
    }
}

@Composable
@Preview
fun ChatWelcomeScreenPreview() {
    KoalaTheme {
        ChatRoomScreen(
            numberOfPeople = 24,
            bestKeyword = "수강신청"
        ) {

        }
    }
}