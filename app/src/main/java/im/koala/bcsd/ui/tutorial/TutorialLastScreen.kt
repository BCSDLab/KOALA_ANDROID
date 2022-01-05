package im.koala.bcsd.ui.tutorial

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import androidx.navigation.NavController
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaCircularCheckBox
import im.koala.bcsd.ui.login.LoginActivity
import im.koala.bcsd.ui.theme.*


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun TutorialLastScreen(navController: NavController){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(top = 64.dp))

        Text(text = stringResource(id = R.string.tutorial_chatting_title),
            modifier = Modifier
                .height(36.dp)
                .width(111.dp)
            ,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = colorResource(R.color.koala_black),
        )

        Spacer(modifier = Modifier.padding(top = 23.5.dp))

        Canvas(
            modifier = Modifier
                .height(1.dp)
                .width(27.dp)){
            drawRect(color = Black)
        }

        Spacer(modifier = Modifier.padding(top = 11.5.dp))

        Text(
            text = stringResource(id = R.string.tutorial_chatting_content),
            modifier = Modifier
                .width(251.dp)
                .height(48.dp),
            color = colorResource(id = R.color.koala_black),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(top = 30.dp))

        Box{
            Box(modifier = Modifier.padding(top = 15.dp)) {
                Canvas(
                    modifier = Modifier
                        .padding(start = 45.7.dp)
                        .size(221.562.dp, 101.989.dp)
                        .shadow(elevation = 4.dp)
                ){
                    drawRoundRect(
                        color= Gray,
                        cornerRadius = CornerRadius(10.dp.toPx(),10.dp.toPx())
                    )
                }

                Canvas(
                    modifier = Modifier
                        .padding(start = 24.6.dp, top = 30.5.dp)
                        .size(266.109.dp, 123.09.dp)
                        .shadow(elevation = 4.dp)
                ){
                    drawRoundRect(
                        color= GrayDisabled,
                        cornerRadius = CornerRadius(10.dp.toPx(),10.dp.toPx())
                    )
                }

                Box(modifier = Modifier.padding(top=68.dp)){
                    Canvas(
                        modifier = Modifier
                            .size(313.dp, 145.363.dp)
                            .shadow(elevation = 4.dp)
                    ){
                        drawRoundRect(
                            color= White,
                            cornerRadius = CornerRadius(10.dp.toPx(),10.dp.toPx())
                        )
                    }

                    Column(modifier = Modifier
                        .padding(start = 20.dp,top=16.dp)
                    ){
                        Row{
                            Image(
                                painter = painterResource(id = R.drawable.tutorial_last_page_icon1),
                                contentDescription = "아이콘1",
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .size(32.dp)
                            )

                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Row(){
                                    Text(
                                        text = "쭈꾸리",
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.koala_black)
                                    )

                                    Text(
                                        text = "18:30",
                                        fontSize = 11.sp,
                                        color = Gray,
                                        modifier = Modifier.padding(start = 4.dp,top=3.dp)
                                    )
                                }

                                Text(
                                    text = "안녕안녕요~~반가워요!",
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.koala_black),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(top = 24.dp))

                        Row{
                            Image(
                                painter = painterResource(id = R.drawable.tutorial_last_page_icon2),
                                contentDescription = "아이콘2",
                                modifier = Modifier
                                    .padding(top = 6.dp)
                                    .size(32.dp)
                            )

                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Row{
                                    Text(
                                        text = "ddsndms",
                                        fontSize = 14.sp,
                                        color = colorResource(id = R.color.koala_black)
                                    )

                                    Text(
                                        text = "18:31",
                                        fontSize = 11.sp,
                                        color = Gray,
                                        modifier = Modifier.padding(start = 4.dp,top=3.dp)
                                    )
                                }

                                Text(
                                    text = "반갑습니다ㅋ 공지 확인 하셨죠?",
                                    fontSize = 12.sp,
                                    color = colorResource(id = R.color.koala_black),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.tutorial_last_page_message),
                contentDescription = null,
                modifier = Modifier.size(95.dp,73.152.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.tutorial_last_page_person),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 201.dp, top = 121.3.dp)
                    .size(108.056.dp, 143.096.dp)
            )
        }

        Row(modifier= Modifier
            .padding(top = 41.6.dp)
            .width(80.dp)){
            KoalaCircularCheckBox(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
                    .border(8.dp, color = GrayDisabled, shape = CircleShape)
            )

            Spacer(modifier = Modifier.padding(start = 16.dp))

            KoalaCircularCheckBox(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
                    .border(8.dp, color = GrayDisabled, shape = CircleShape)
            )

            Spacer(modifier = Modifier.padding(start = 16.dp))

            KoalaCircularCheckBox(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
                    .border(8.dp, color = GrayDisabled, shape = CircleShape)
            )

            Spacer(modifier = Modifier.padding(start = 16.dp))

            KoalaCircularCheckBox(
                checked = true,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
            )
        }

        Spacer(modifier = Modifier.padding(top = 24.dp))

        Button(
            onClick = {
                    context.startActivity(Intent(context,LoginActivity::class.java))
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
                fontSize = 14.sp
            )
        }

    }
}

@Preview
@Composable
fun PreviewTutorialLastScreen(){
//    KoalaTheme() {
//        Surface(color = GrayBorder) {
//            TutorialLastScreen()
//        }
//    }
}