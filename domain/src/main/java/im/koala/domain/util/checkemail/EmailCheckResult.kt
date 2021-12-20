package im.koala.domain.util.checkemail

sealed class EmailCheckResult {
    object OK: EmailCheckResult()
    object NoSuchInputError: EmailCheckResult()
    object NotEmailFormatError: EmailCheckResult()
    object EmailDuplicatedError: EmailCheckResult()
}