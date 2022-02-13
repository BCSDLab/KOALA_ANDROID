package im.koala.data.repository.local

import im.koala.domain.entity.keyword.Site

interface KeywordLocalDataSource {
    fun getSiteLocalizedMessage(
        site: Site
    ): String
}