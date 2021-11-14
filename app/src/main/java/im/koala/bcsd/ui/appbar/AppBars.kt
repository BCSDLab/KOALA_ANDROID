package im.koala.bcsd.ui.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.R
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

@Composable
fun KoalaTextAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)
) {
    Column {
        TopAppBar(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            elevation = 0.dp,
            contentPadding = PaddingValues(
                horizontal = 8.dp
            )
        ) {
            Box {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colors.onPrimary
                ) {
                    if (onBackClick != null) {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            KoalaBackButton(onClick = onBackClick)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProvideTextStyle(
                            value = TextStyle(
                                color = MaterialTheme.colors.onPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                text = title
                            )
                        }
                    }

                    ProvideTextStyle(
                        value = TextStyle(
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            content = actions
                        )
                    }
                }
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = GrayBorder
        )
    }
}

@Composable
fun KoalaAppBar() {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.ic_koala_logo),
                contentDescription = stringResource(
                    id = R.string.koala_logo_content_description
                )
            )
        }

    )
}

@Composable
fun KoalaBackButton(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_nav_back),
            contentDescription = stringResource(id = R.string.back_button_content_description),
        )
    }
}

@Composable
@Preview
private fun KoalaAppBarPreview() {
    KoalaTheme(darkTheme = false) {
        KoalaAppBar()
    }
}

@Composable
@Preview
private fun KoalaTextAppBarWithBackButtonPreview() {
    KoalaTheme(darkTheme = false) {
        Column(
            modifier = Modifier.background(color = MaterialTheme.colors.surface)
        ) {
            KoalaTextAppBar(
                title = "키워드 수정하기",
                onBackClick = {}
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "완료"
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
        }
    }
}

@Composable
@Preview
private fun KoalaTextAppBarPreview() {
    KoalaTheme(darkTheme = false) {
        Column(
            modifier = Modifier.background(color = MaterialTheme.colors.surface)
        ) {
            KoalaTextAppBar(
                title = "키워드 수정하기"
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = "완료"
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
        }
    }
}