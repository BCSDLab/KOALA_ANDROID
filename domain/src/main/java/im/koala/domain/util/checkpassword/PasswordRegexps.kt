package im.koala.domain.util.checkpassword

object PasswordRegexps {
    val REGEX_MATCH_SUPPORTED_CHARACTERS = "^[a-zA-Z0-9`₩~!@#\$%<>^&*()\\-=+_?:;\"',.{}|\\[\\]/\\\\]*".toRegex()
    val REGEX_CONTAINS_SPECIAL_CHARACTER = ".*[`₩~!@#\$%<>^&*()\\-=+_?:;\"',.{}|\\[\\]/\\\\]+.*".toRegex()
    val REGEX_CONTAINS_ENGLISH = ".*[a-zA-Z]+.*".toRegex()
    val REGEX_CONTAINS_NUMBER = ".*[0-9]+.*".toRegex()
}