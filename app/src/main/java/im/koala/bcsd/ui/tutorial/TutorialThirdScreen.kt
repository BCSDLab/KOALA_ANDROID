package im.koala.bcsd.ui.tutorial

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
import androidx.compose.ui.Modifier
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
import im.koala.bcsd.ui.theme.*

@Composable
fun TutorialThirdScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(top = 64.dp))

        Text(text = stringResource(id = R.string.tutorial_storage_title),
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
            text = stringResource(id = R.string.tutorial_storage_content),
            modifier = Modifier
                .width(251.dp)
                .height(48.dp),
            color = colorResource(id = R.color.koala_black),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(top=10.dp))

        Box{
            Image(
                painter = painterResource(id = R.drawable.tutorial_third_page_example),
                contentDescription = "키워드 예시",
                modifier = Modifier
                    .padding(top = 28.dp)
                    .size(284.dp, 229.dp)

            )

            Image(
                painter = painterResource(id = R.drawable.tutorial_third_page_person),
                contentDescription = "키워드 사람",
                modifier = Modifier
                    .padding(start = 190.dp, top = 163.dp)
                    .size(69.663.dp, 123.806.dp)

            )

            Image(
                painter = painterResource(id = R.drawable.tutorial_third_page_pencil),
                contentDescription = "키워드 연필",
                modifier = Modifier
                    .padding(start = 24.dp)
                    .size(77.132.dp, 55.431.dp)

            )
        }

        Row(modifier= Modifier
            .padding(top = 39.2.dp)
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
                checked = true,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
            )

            Spacer(modifier = Modifier.padding(start = 16.dp))

            KoalaCircularCheckBox(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
                    .border(8.dp, color = GrayDisabled, shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.padding(top = 24.dp))

        Button(
            onClick = {
                      navController.navigate(TutorialScreen.LastScreen.route)
            },
            modifier = Modifier
                .size(328.dp, 48.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = White
            )
        ) {
            Text(
                text = stringResource(id = R.string.signup_next),
                color = colorResource(id = R.color.koala_black),
                fontSize = 14.sp
            )
        }

    }
}

@Preview
@Composable
fun PreviewTutorialThirdScreen(){
//    KoalaTheme {
//        Surface(color = GrayBorder) {
//            TutorialThirdScreen()
//        }
//    }
}