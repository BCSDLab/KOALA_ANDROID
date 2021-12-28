package im.koala.bcsd.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    open val vmExceiptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }
}