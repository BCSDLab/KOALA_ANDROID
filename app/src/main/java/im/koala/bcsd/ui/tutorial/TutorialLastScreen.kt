package im.koala.bcsd.ui.tutorial

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import im.koala.bcsd.R
import im.koala.bcsd.ui.login.LoginActivity
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.Gray
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.White
import im.koala.bcsd.ui.theme.KoalaTheme

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun TutorialLastScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(top = 64.dp))

        Text(
            text = stringResource(id = R.string.tutorial_chatting_title),
            modifier = Modifier.size(111.dp, 36.dp),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = colorResource(R.color.koala_black),
        )

        Spacer(modifier = Modifier.padding(top = 23.5.dp))

        Canvas(
            modifier = Modifier
                .size(27.dp, 1.dp)
        ) {
            drawRect(color = Black)
        }

        Spacer(modifier = Modifier.padding(top = 11.5.dp))

        Text(
            text = stringResource(id = R.string.tutorial_chatting_content),
            modifier = Modifier
                .size(251.dp, 48.dp),
            color = colorResource(id = R.color.koala_black),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(top = 30.dp))

        Box {
            Box(modifier = Modifier.padding(top = 15.dp)) {
                Canvas(
                    modifier = Modifier
                        .padding(start = 45.7.dp)
                        .size(221.6.dp, 102.dp)
                        .shadow(elevation = 4.dp)
                ) {
                    drawRoundRect(
                        color = Gray,
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                    )
                }

                Canvas(
                    modifier = Modifier
                        .padding(start = 24.6.dp, top = 30.5.dp)
                        .size(266.1.dp, 123.1.dp)
                        .shadow(elevation = 4.dp)
                ) {
                    drawRoundRect(
                        color = GrayDisabled,
                        cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                    )
                }

                Box(modifier = Modifier.padding(top = 68.dp)) {
                    Canvas(
                        modifier = Modifier
                            .size(313.dp, 145.4.dp)
                            .shadow(elevation = 4.dp)
                    ) {
                        drawRoundRect(
                            color = White,
                            cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx())
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp, top = 16.dp)
                    ) {
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_tutorial_profile_yellow),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .size(32.dp)
                            )

                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Row {
                                    Text(
                                        text = stringResource(id = R.string.tutorial_first_chatting_name),
                                        modifier = Modifier.size(39.dp, 21.dp),
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.koala_black),
                                        style = MaterialTheme.typography.h2
                                    )

                                    Text(
                                        text = stringResource(id = R.string.tutorial_first_chatting_time),
                                        fontSize = 11.sp,
                                        color = Gray,
                                        modifier = Modifier.padding(start = 4.dp, top = 3.dp)
                                    )
                                }

                                Text(
                                    text = stringResource(id = R.string.tutorial_first_chatting_context),
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.koala_black),
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(top = 24.dp))

                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.ic_tutorial_profile_red),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .size(32.dp)
                            )

                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Row {
                                    Text(
                                        text = stringResource(id = R.string.tutorial_second_chatting_name),
                                        fontSize = 14.sp,
                                        modifier = Modifier.size(62.dp, 21.dp),
                                        color = colorResource(id = R.color.koala_black),
                                        style = MaterialTheme.typography.h2
                                    )

                                    Text(
                                        text = stringResource(id = R.string.tutorial_second_chatting_time),
                                        fontSize = 11.sp,
                                        color = Gray,
                                        modifier = Modifier.padding(start = 4.dp, top = 3.dp)
                                    )
                                }

                                Text(
                                    text = stringResource(id = R.string.tutorial_second_chatting_context),
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.koala_black),
                                    modifier = Modifier.padding(top = 4.dp),
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_tutorial_last_page_message),
                contentDescription = null,
                modifier = Modifier.size(95.dp, 73.2.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_tutorial_last_page_person),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 201.dp, top = 113.dp)
                    .size(108.1.dp, 151.1.dp)
            )
        }

        Spacer(modifier = Modifier.padding(top = 41.6.dp))

        TutorialProcessDot(pageList = listOf(false, false, false, true))

        Spacer(modifier = Modifier.padding(top = 24.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, LoginActivity::class.java))
            },
            modifier = Modifier
                .size(328.dp, 48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Black
            )
        ) {
            Text(
                text = stringResource(id = R.string.tutorial_start),
                color = colorResource(id = R.color.white),
                fontSize = 14.sp,
                style = MaterialTheme.typography.button
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview
@Composable
fun PreviewTutorialLastScreen() {
    KoalaTheme {
        Surface(color = GrayBorder) {
            TutorialLastScreen()
        }
    }
}