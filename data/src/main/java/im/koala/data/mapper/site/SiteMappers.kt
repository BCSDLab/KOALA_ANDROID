package im.koala.data.mapper.site

import im.koala.domain.entity.keyword.Site

fun String.toSite() = when (this) {
    "PORTAL" -> Site.Portal
    "DORM" -> Site.Dorm
    "YOUTUBE" -> Site.Youtube
    "FACEBOOK" -> Site.Facebook
    "INSTAGRAM" -> Site.Instagram
    else -> throw IllegalStateException("No matching site!")
}