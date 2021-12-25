package im.koala.domain.util.checkemail

object EmailChecker {
    fun isEmail(email: String) = email.matches(EmailRegexps.EMAIL_REGEX)
}