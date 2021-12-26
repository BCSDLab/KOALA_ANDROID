package im.koala.domain.util

fun String.toSHA256() = StringUtil.hashString(this, StringUtil.SHA256)