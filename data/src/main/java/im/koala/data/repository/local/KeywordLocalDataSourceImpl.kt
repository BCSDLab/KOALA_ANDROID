package im.koala.data.repository.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import im.koala.data.R
import im.koala.domain.entity.keyword.Site
import javax.inject.Inject

class KeywordLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : KeywordLocalDataSource {

    private val siteStringMap = mapOf(
        Site.All to context.getString(R.string.keyword_site_all),
        Site.Dorm to context.getString(R.string.keyword_site_dorm),
        Site.Facebook to context.getString(R.string.keyword_site_facebook),
        Site.Instagram to context.getString(R.string.keyword_site_instagram),
        Site.Portal to context.getString(R.string.keyword_site_portal),
        Site.Youtube to context.getString(R.string.keyword_site_youtube)
    )

    override fun getSiteLocalizedMessage(site: Site): String {
        return siteStringMap[site] ?: throw IllegalStateException("Unknown Site: $site")
    }
}