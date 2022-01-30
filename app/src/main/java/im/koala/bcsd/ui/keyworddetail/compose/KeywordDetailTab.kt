package im.koala.bcsd.ui.keyworddetail.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import im.koala.bcsd.ui.theme.GrayBorder
import im.koala.domain.entity.keyword.Site

@Composable
fun KeywordDetailTab(
    modifier: Modifier = Modifier,
    sitePair: List<Pair<Site, String>>,
    selectedSite: Site = Site.All,
    onTabItemSelected: (item: Site) -> Unit
) {
    val sites = sitePair.map { it.first }
    val siteNames = sitePair.map { it.second }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomStart
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = GrayBorder
        )

        ScrollableTabRowWithMinTabWidth(
            modifier = modifier.fillMaxWidth(),
            selectedTabIndex = sites.indexOf(selectedSite),
            edgePadding = 8.dp,
            backgroundColor = Color.Transparent,
            divider = {
                TabRowDefaults.Divider(thickness = 0.dp)
            }
        ) {
            sitePair.forEach { (site, siteName) ->
                Tab(
                    selected = site == selectedSite,
                    onClick = { onTabItemSelected(site) }
                ) {

                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                        text = siteName,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = if (site == selectedSite) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}