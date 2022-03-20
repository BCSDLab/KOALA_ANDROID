package im.koala.bcsd.ui

import androidx.activity.ComponentActivity

abstract class BaseLoginActivity : ComponentActivity() {
    abstract val viewModel: BaseViewModel?
}