package im.koala.bcsd.ui.keyword

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.insets.ProvideWindowInsets
import im.koala.bcsd.R
import im.koala.bcsd.state.NetworkState
import im.koala.bcsd.ui.login.DrawImageView
import im.koala.bcsd.ui.main.MainScreenBottomTab
import im.koala.bcsd.ui.main.MainViewModel
import im.koala.bcsd.ui.theme.Yellow
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse

@Composable
fun KeywordScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    viewModel: MainViewModel,
    selectKeyword: (MainScreenBottomTab, Int) -> Unit
) {
    val keywordListState by viewModel.keywordState.observeAsState(NetworkState.Uninitialized)
    viewModel.executeGetKeywordList()

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
            when (keywordListState) {
                is NetworkState.Loading -> {
                }
                is NetworkState.Success<*> -> {
                    val response = (keywordListState as NetworkState.Success<*>).data as MutableList<KeywordResponse>
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
                        keywordList = response,
                        selectKeyword = selectKeyword
                    )
                }
                is NetworkState.Fail<*> -> {
                    val response = (keywordListState as NetworkState.Fail<*>).data as CommonResponse
                    when (response) {
                        CommonResponse.UNKOWN -> response.errorMessage = stringResource(id = R.string.network_unkown_error)
                    }
                    Toast.makeText(LocalContext.current, response.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
@Composable
fun DrawAddKeywordButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            // .padding(horizontal = 24.dp)
            .clickable(
                onClick = { Log.e("asdfasdf", "addKeyword") }
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

@Composable
fun DrawLazyColumView(
    modifier: Modifier,
    lazyListState: LazyListState,
    keywordList: MutableList<KeywordResponse>,
    selectKeyword: (MainScreenBottomTab, Int) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {

        items(keywordList) { it ->
            DrawKeywordItem(keyword = it, selectKeyword)
        }
        item {
            DrawAddKeywordButton()
        }
    }
}

@Composable
fun DrawKeywordItem(keyword: KeywordResponse, selectKeyword: (MainScreenBottomTab, Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            // .padding(horizontal = 24.dp)
            .clickable(
                onClick = {
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