package im.koala.bcsd.ui.keyword

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import im.koala.bcsd.R
import im.koala.bcsd.navigation.NavScreen
import im.koala.bcsd.ui.login.DrawImageView
import im.koala.bcsd.ui.main.MainScreenBottomTab
import im.koala.bcsd.ui.main.MainViewModel
import im.koala.bcsd.ui.theme.Yellow
import im.koala.domain.model.KeywordResponse

@ExperimentalMaterialApi
@Composable
fun KeywordScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    viewModel: MainViewModel,
    selectKeyword: (MainScreenBottomTab, Int) -> Unit,
    navController: NavController,
) {
    val keywordUi = viewModel.keywordUi

    ProvideWindowInsets {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (logoImageView, keywordText, keywordSetButton, divider, keywordLazyColumView, keywordAddButton) = createRefs()

            DrawImageView(
                modifier = Modifier.constrainAs(logoImageView) {
                    top.linkTo(parent.top, margin = 29.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
                drawableId = R.drawable.ic_koala_logo_small
            )

            Text(
                modifier = Modifier.constrainAs(keywordText) {
                    top.linkTo(logoImageView.bottom, margin = 40.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                },
                text = stringResource(id = R.string.keyword),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1
            )
            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier.constrainAs(keywordSetButton) {
                    top.linkTo(logoImageView.bottom, margin = 40.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.setting),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body1
                )
            }
            DrawHorizantalDivider(
                modifier = Modifier
                    .constrainAs(divider) {
                        top.linkTo(keywordText.bottom, margin = 15.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }
                    .height(1.dp),
                color = MaterialTheme.colors.onBackground
            )
            DrawLazyColumView(
                modifier = Modifier.constrainAs(keywordLazyColumView) {
                    top.linkTo(divider.bottom, margin = 12.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                lazyListState = lazyListState,
                keywordList = keywordUi.value.keywordList,
                selectKeyword = selectKeyword,
                deleteKeyword = { viewModel.deleteKeyword(it) },
                navController = navController,
            )
        }
    }
}

@Composable
fun DrawAddKeywordButton(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            // .padding(horizontal = 24.dp)
            .clickable(
                onClick = { navController.navigate(NavScreen.KeywordAdd.route) }
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Image(painter = painterResource(id = R.drawable.ic_plus), contentDescription = null)
        Text(
            text = stringResource(id = R.string.add_keyword),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun DrawHorizantalDivider(modifier: Modifier, color: Color) {
    Divider(
        color = color,
        modifier = modifier
    )
}

@ExperimentalMaterialApi
@Composable
fun DrawLazyColumView(
    modifier: Modifier,
    lazyListState: LazyListState,
    keywordList: MutableList<KeywordResponse>,
    selectKeyword: (MainScreenBottomTab, Int) -> Unit,
    deleteKeyword: (keyword: String) -> Unit,
    navController: NavController,
) {
    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        itemsIndexed(
            items = keywordList,
            key = { _, item ->
                item.hashCode()
            }
        ) { _, item ->
            val state = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        deleteKeyword(item.name)
                    }
                    true
                },
            )
            SwipeToDismiss(
                state = state,
                background = {
                    val color = when (state.dismissDirection) {
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Red
                        null -> Color.Transparent
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = color)
                            .padding(
                                start = 255.dp,
                                end = 31.dp,
                                top = 13.dp,
                                bottom = 12.dp
                            )
                    ) {
                        Text(
                            text = "제거",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                dismissContent = {
                    DrawKeywordItem(keyword = item, selectKeyword)
                },
                directions = setOf(DismissDirection.EndToStart),
            )
        }
        item {
            DrawAddKeywordButton(navController)
        }
    }
}

@Composable
fun DrawKeywordItem(keyword: KeywordResponse, selectKeyword: (MainScreenBottomTab, Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .height(65.dp)
            // .padding(horizontal = 24.dp)
            .clickable(
                onClick = {
                    /* TODO KeywordDetail 화면 전환 */
                    selectKeyword(MainScreenBottomTab.KEYWORD, keyword.id)
                    Log.e("asdfasdf", keyword.id.toString())
                }
            ),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            modifier = Modifier.weight(1.0f),
            text = keyword.name,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.body1
        )

        Text(
            modifier = Modifier
                .width(24.dp)
                .height(18.dp)
                .background(Yellow),
            text = keyword.noticeNum.toString(),
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}