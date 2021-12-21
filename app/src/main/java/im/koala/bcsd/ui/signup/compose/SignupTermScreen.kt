package im.koala.bcsd.ui.signup.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme
import kotlinx.coroutines.launch

@Composable
fun SignupTermScreen(
    onSignUpStateChanged: (signUpEnabled: Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val isCheckedTermsPrivacy = rememberSaveable { mutableStateOf(false) }
    val isCheckedTermsKoala = rememberSaveable { mutableStateOf(false) }

    val isOpenedTermsPrivacy = rememberSaveable { mutableStateOf(false) }
    val isOpenedTermsKoala = rememberSaveable { mutableStateOf(false) }

    fun animateScrollToItem(index: Int) {
        coroutineScope.launch {
            listState.animateScrollToItem(index = index)
        }
    }

    fun isSignUpEnabled() = isCheckedTermsPrivacy.value && isCheckedTermsKoala.value

    LazyColumn(
        state = listState
    ) {
        items(3) { index ->
            when (index) {
                0 -> Column {
                    SignupSubtitle(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 24.dp,
                            bottom = 12.dp
                        ),
                        text1 = stringResource(R.string.signup_subtitle_terms),
                        text2 = stringResource(R.string.signup_subtitle_terms_description)
                    )

                    SignupTermCheckBox(
                        text = stringResource(R.string.signup_terms_agree_all),
                        checked = isCheckedTermsPrivacy.value && isCheckedTermsKoala.value,
                        onCheckedChange = {
                            isCheckedTermsPrivacy.value = it
                            isCheckedTermsKoala.value = it
                            if (it) {
                                isOpenedTermsPrivacy.value = false
                                isOpenedTermsKoala.value = false
                            }
                            onSignUpStateChanged(isSignUpEnabled())
                        },
                        termsText = null
                    )

                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        color = GrayBorder
                    )
                }
                1 -> SignupTermCheckBox(
                    text = stringResource(R.string.signup_terms_privacy),
                    checked = isCheckedTermsPrivacy.value,
                    isOpened = isOpenedTermsPrivacy.value,
                    onOpenButtonClicked = {
                        isOpenedTermsPrivacy.value = it
                        animateScrollToItem(0)
                    },
                    onCheckedChange = {
                        isCheckedTermsPrivacy.value = it
                        if (it) {
                            isOpenedTermsPrivacy.value = false
                        }
                        onSignUpStateChanged(isSignUpEnabled())
                    },
                    termsText = stringResource(R.string.signup_terms_privacy_detail)
                )

                2 -> SignupTermCheckBox(
                    text = stringResource(R.string.signup_terms_koala),
                    checked = isCheckedTermsKoala.value,
                    isOpened = isOpenedTermsKoala.value,
                    onOpenButtonClicked = {
                        isOpenedTermsKoala.value = it
                        animateScrollToItem(1)
                    },
                    onCheckedChange = {
                        isCheckedTermsKoala.value = it
                        if (it) {
                            isOpenedTermsKoala.value = false
                        }
                        onSignUpStateChanged(isSignUpEnabled())
                    },
                    termsText = stringResource(R.string.signup_terms_koala_detail)
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun SignupTermScreenPreview() {
    KoalaTheme {
        Scaffold(
            topBar = {
                KoalaTextAppBar(
                    title = stringResource(R.string.app_bar_title_signup),
                    showBackButton = true
                ) {}
            }
        ) {
            SignupTermScreen {

            }
        }
    }
}