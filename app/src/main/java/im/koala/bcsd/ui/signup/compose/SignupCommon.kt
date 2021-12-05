package im.koala.bcsd.ui.signup.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun SignupSubtitle(
    text1: String,
    text2: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = text1,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )
        Text(
            text = text2,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun SignupPermissionItem(
    icon: @Composable (() -> Unit),
    permissionName: String,
    permissionDescription: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
        ) {
            icon()
        }

        Column(
            modifier = Modifier.padding(start = 24.dp)
        ) {
            Text(
                text = permissionName,
                modifier = Modifier,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondary
            )
            Text(
                text = permissionDescription,
                modifier = Modifier.padding(top = 2.dp),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}


@Preview
@Composable
fun SignupSubtitlePreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            SignupSubtitle(
                text1 = "약관 동의",
                text2 = "원활한 사용을 위하여 필수 약관 동의가 필요합니다."
            )
        }
    }
}

@Preview
@Composable
fun SignupPermissionItemPreview() {
    KoalaTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
        ) {
            SignupPermissionItem(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_bell),
                        contentDescription = ""
                    )
                },
                permissionName = "알림 (필수)",
                permissionDescription = "키워드 알림시 필수 이용"
            )
        }
    }
}