package im.koala.bcsd.ui.tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
class TutorialActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            KoalaTheme {
                Surface(color = GrayBorder) {
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
                            TutorialLastScreen()
                        }
                    }
                }
            }
        }
    }
}