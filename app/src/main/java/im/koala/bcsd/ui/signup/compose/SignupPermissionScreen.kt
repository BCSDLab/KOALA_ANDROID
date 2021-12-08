package im.koala.bcsd.ui.signup.compose

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun SignupPermissionScreen() {
    Column(
        modifier = Modifier.verticalScroll(state = ScrollState(0))
    ) {
        SignupSubtitle(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
            text1 = stringResource(R.string.signup_subtitle_permission),
            text2 = stringResource(R.string.signup_subtitle_permission_description)
        )

        SignupPermissionItem(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = ""
                )
            },
            permissionName = stringResource(R.string.signup_permission_item_notification),
            permissionDescription = stringResource(R.string.signup_permission_item_notification_description)
        )

        SignupPermissionItem(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = ""
                )
            },
            permissionName = stringResource(R.string.signup_permission_item_camera),
            permissionDescription = stringResource(R.string.signup_permission_item_camera_description)
        )

        SignupPermissionItem(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_files_folder),
                    contentDescription = ""
                )
            },
            permissionName = stringResource(R.string.signup_permission_item_storage),
            permissionDescription = stringResource(R.string.signup_permission_item_notification_storage)
        )

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = GrayBorder
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.onBackground) {
                Icon(painter = painterResource(id = R.drawable.ic_signup_permission_info), contentDescription = "")
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(R.string.signup_permission_info),
                    style = MaterialTheme.typography.body2,
                    fontSize = 11.sp,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SignupPermissionScreenPreview() {
    KoalaTheme {
        Scaffold(
            topBar = {
                KoalaTextAppBar(
                    title = stringResource(R.string.app_bar_title_signup),
                    showBackButton = true
                ) {}
            }
        ) {
            SignupPermissionScreen()
        }
    }
}