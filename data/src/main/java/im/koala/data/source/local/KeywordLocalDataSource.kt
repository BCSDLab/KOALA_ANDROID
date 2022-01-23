package im.koala.data.source.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import im.koala.data.R
import im.koala.data.constant.ERROR_MESSAGE_NOT_USE_LOCAL
import im.koala.data.source.KeywordDataSource
import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.entity.keyword.Site
import javax.inject.Inject

class KeywordLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) : KeywordDataSource {

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

    override fun getKeywordNotices(keyword: String): List<KeywordNotice> {
        throw IllegalAccessException(ERROR_MESSAGE_NOT_USE_LOCAL)
    }
}