package im.koala.bcsd.ui.signup.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import im.koala.bcsd.R
import im.koala.bcsd.ui.appbar.KoalaTextAppBar
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.bcsd.ui.theme.KoalaTheme

@ExperimentalAnimationApi
@Composable
fun SignupTermScreen(
    isCheckedTermsPrivacy : MutableState<Boolean>,
    isCheckedTermsKoala : MutableState<Boolean>
) {
    val isOpenedTermsPrivacyPanel = rememberSaveable { mutableStateOf(false) }
    val isOpenedTermsKoalaPanel = rememberSaveable { mutableStateOf(false) }

    val openTermsPrivacyPanelIconAngle: Float by animateFloatAsState(if (isOpenedTermsPrivacyPanel.value) 90f else 0f)
    val openTermsKoalaPanelIconAngle: Float by animateFloatAsState(if (isOpenedTermsKoalaPanel.value) 90f else 0f)

    Column(
        modifier = Modifier.verticalScroll(state = ScrollState(0))
    ) {
        SignupSubtitle(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp),
            text1 = stringResource(R.string.signup_subtitle_terms),
            text2 = stringResource(R.string.signup_subtitle_terms_description)
        )

        SignupCheckBox(
            text = stringResource(R.string.signup_terms_agree_all),
            checked = isCheckedTermsPrivacy.value && isCheckedTermsKoala.value,
            onCheckedChange = {
                isCheckedTermsPrivacy.value = it
                isCheckedTermsKoala.value = it
            }
        )

        Divider(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = GrayBorder
        )

        SignupCheckBox(
            text = stringResource(R.string.signup_terms_privacy),
            checked = isCheckedTermsPrivacy.value,
            onCheckedChange = {
                isCheckedTermsPrivacy.value = it
            }
        ) {
            Icon(
                modifier = Modifier
                    .clickable {
                        isOpenedTermsPrivacyPanel.value = !isOpenedTermsPrivacyPanel.value
                    }
                    .rotate(openTermsPrivacyPanelIconAngle)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground
            )
        }

        AnimatedVisibility(visible = isOpenedTermsPrivacyPanel.value) {
            SignupTermBox(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                termsText = stringResource(R.string.signup_terms_privacy_detail)
            )
        }


        SignupCheckBox(
            text = stringResource(R.string.signup_terms_koala),
            checked = isCheckedTermsKoala.value,
            onCheckedChange = {
                isCheckedTermsKoala.value = it
            }
        ) {
            Icon(
                modifier = Modifier
                    .clickable {
                        isOpenedTermsKoalaPanel.value = !isOpenedTermsKoalaPanel.value
                    }
                    .rotate(openTermsKoalaPanelIconAngle)
                    .padding(12.dp),
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "",
                tint = MaterialTheme.colors.onBackground
            )
        }

        AnimatedVisibility(visible = isOpenedTermsKoalaPanel.value) {
            SignupTermBox(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                termsText = stringResource(R.string.signup_terms_koala_detail)
            )
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
            SignupTermScreen(
                rememberSaveable { mutableStateOf(false) },
                rememberSaveable { mutableStateOf(false) },
            )
        }
    }
}