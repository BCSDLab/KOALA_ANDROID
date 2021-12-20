package im.koala.domain.util.checkid

sealed class IdCheckResult {
    object OK: IdCheckResult()
    object NoSuchInputError: IdCheckResult()
    object IdDuplicatedError: IdCheckResult()
}