package im.koala.bcsd.ui.tutorial

sealed class TutorialScreen(val route: String) {
    object FirstScreen : TutorialScreen("first_screen")
    object SecondScreen : TutorialScreen("second_screen")
    object ThirdScreen : TutorialScreen("third_screen")
    object LastScreen : TutorialScreen("last_screen")
}