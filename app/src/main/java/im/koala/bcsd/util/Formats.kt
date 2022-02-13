package im.koala.bcsd.util

import java.text.SimpleDateFormat
import java.util.Date

const val DATE_FORMAT_KEYWORD_DETAIL_ITEMS = "M/d - HH:mm"

fun Long.toFormattedDate(dateFormat: String) =
    SimpleDateFormat(dateFormat).format(Date(this))