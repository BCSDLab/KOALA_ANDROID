package im.koala.bcsd.ui.tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class TutorialActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = TutorialScreen.FirstScreen.route){
                composable(route = TutorialScreen.FirstScreen.route){
                    TutorialFirstScreen(navController)
                }
                composable(route = TutorialScreen.SecondScreen.route){
                    TutorialSecondScreen(navController)
                }
                composable(route = TutorialScreen.ThirdScreen.route){
                    TutorialThirdScreen(navController)
                }
                composable(route = TutorialScreen.LastScreen.route){
                    TutorialLastScreen(navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview2() {
//    KoalaTheme {
//        Surface(color = GrayBorder) {
//            TutorialSecondScreen()
//        }
//    }
}