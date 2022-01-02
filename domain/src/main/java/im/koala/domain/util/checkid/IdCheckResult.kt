package im.koala.domain.util.checkid

sealed class IdCheckResult {
    object OK : IdCheckResult()
    object Loading : IdCheckResult()
    object NoSuchInputError : IdCheckResult()
    object IdDuplicatedError : IdCheckResult()
}