package im.koala.bcsd.ui.tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import im.koala.bcsd.R
import im.koala.bcsd.ui.button.KoalaCheckBox
import im.koala.bcsd.ui.button.KoalaCircularCheckBox
import im.koala.bcsd.ui.button.KoalaToggle
import im.koala.bcsd.ui.theme.Black
import im.koala.bcsd.ui.theme.GrayDisabled
import im.koala.bcsd.ui.theme.KoalaTheme


class TutorialActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "First",){
                composable("first"){
                    TutorialFirstScreen()
                }
                composable("second"){
                    TutorialSecondScreen()
                }
                composable("third"){
                    TutorialThirdScreen()
                }
                composable("last"){
                    TutorialLastScreen()
                }
            }
            KoalaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun TutorialFirstScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.keyword),
            modifier = Modifier
                .height(36.dp)
                .width(111.dp)
                ,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = colorResource(R.color.koala_black),
        )

        Canvas(
            modifier = Modifier
                .padding(top = 23.5.dp)
                .height(1.dp)
                .width(27.dp)){
            drawRect(color = Black)
        }

        Text(
            text = stringResource(id = R.string.tutorial_keyword_content),
            modifier = Modifier
                .width(251.dp)
                .height(48.dp)
                .padding(top = 11.5.dp),
            color = colorResource(id = R.color.koala_black),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Row(modifier=Modifier.padding(top = 47.dp).width(80.dp)){
            KoalaCircularCheckBox(
                checked = true,
                onCheckedChange = {},
                modifier = Modifier
                .size(8.dp)
                .width(8.dp)
                .height(8.dp)

            )
            KoalaCircularCheckBox(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
                    .border(8.dp,color= GrayDisabled,shape= CircleShape)

            )
            KoalaCircularCheckBox(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
                    .border(8.dp,color= GrayDisabled,shape= CircleShape)

            )
            KoalaCircularCheckBox(
                checked = false,
                onCheckedChange = {},
                modifier = Modifier
                    .size(8.dp)
                    .border(8.dp,color= GrayDisabled,shape= CircleShape)

            )
        }

    }
}

@Composable
fun TutorialSecondScreen(){

}

@Composable
fun TutorialThirdScreen(){

}

@Composable
fun TutorialLastScreen(){

}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    KoalaTheme {
        TutorialFirstScreen()
    }
}