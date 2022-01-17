package im.koala.data.constant

const val KOALA_API_URL = "https://api.stage.koala.im/"

const val KOALA_API_ERROR_CODE_DUPLICATED_NICKNAME = 124
const val KOALA_API_ERROR_CODE_DUPLICATED_EMAIL = 125
const val KOALA_API_ERROR_CODE_DUPLICATED_ID = 126

const val KOALA_API_URL_USER = "user"
const val KOALA_API_URL_ACCOUNT_CHECK = "$KOALA_API_URL_USER/account-check"
const val KOALA_API_URL_EMAIL_CHECK = "$KOALA_API_URL_USER/email-check"
const val KOALA_API_URL_NICKNAME_CHECK = "$KOALA_API_URL_USER/nickname-check"
const val KOALA_API_URL_SIGN_UP = "$KOALA_API_URL_USER/sing-in"

// SignUp
const val KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE = "존재하는 계정입니다"
const val KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE = "사용 가능한 이메일입니다"
const val KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE = "사용 가능한 닉네임입니다."

// Keyword
const val KOALA_API_URL_KEYWORD = "keyword"
const val KOALA_API_KEYWORD_SEARCH = "$KOALA_API_URL_KEYWORD/search"
const val KOALA_API_KEYWORD_RECOMMENDATION = "$KOALA_API_URL_KEYWORD/recommendation"
const val KOALA_API_KEYWORD_SITE_RECOMMENDATION = "$KOALA_API_URL_KEYWORD/site/recommendation"
const val KOALA_API_KEYWORD_SITE_SEARCH = "$KOALA_API_URL_KEYWORD/site/search"
