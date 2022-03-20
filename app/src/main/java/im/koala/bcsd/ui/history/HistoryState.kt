package im.koala.bcsd.ui.history

sealed class SnackBarState {
    object NoneSnackBar : SnackBarState()
    data class ShowSnackBar(
        val snackBarMessage: String,
        val snackBarCommend: SnackBarCommend,
        val isSuccess: Boolean
    ) : SnackBarState()
}

sealed class SnackBarCommend {
    object DeleteHistory: SnackBarCommend()
    object UndoDeleteHistory: SnackBarCommend()
    object ScrapHistory: SnackBarCommend()
    object UndoScrapHistory: SnackBarCommend()
}