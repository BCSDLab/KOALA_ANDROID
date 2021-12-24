package im.koala.bcsd.ui.main

import androidx.compose.foundation.lazy.LazyListState

data class HomeTabStateHolder(
    val keywordLazyListState: LazyListState,
    val historyLazyListState: LazyListState,
    val chatLazyListState: LazyListState
)