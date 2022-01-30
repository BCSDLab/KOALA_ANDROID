package im.koala.bcsd.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject
@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {
    var isLoading by mutableStateOf(false)

    open val vmExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    }

    inline fun withLoading(
        beforeLoading: () -> Unit = {},
        afterLoading: () -> Unit = {},
        withLoading: () -> Unit
    ) {
        beforeLoading()
        isLoading = true
        withLoading()
        isLoading = false
        afterLoading()
    }
}