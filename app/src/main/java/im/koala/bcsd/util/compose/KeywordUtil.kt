package im.koala.bcsd.util.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import im.koala.bcsd.R
import im.koala.domain.entity.keyword.Site

@Composable
fun Site.LocalizedMessage() : String {
    return when(this) {
        Site.Dorm -> stringResource(id = R.string.keyword_site_dorm)
        Site.Facebook -> stringResource(id = R.string.keyword_site_facebook)
        Site.Instagram -> stringResource(id = R.string.keyword_site_instagram)
        Site.Portal -> stringResource(id = R.string.keyword_site_portal)
        Site.Youtube -> stringResource(id = R.string.keyword_site_youtube)
        Site.All -> stringResource(id = R.string.keyword_site_all)
    }
}