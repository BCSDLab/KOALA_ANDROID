package im.koala.domain.util.checknickname

sealed class NicknameCheckResult {
    object OK: NicknameCheckResult()
    object NoSuchInputError: NicknameCheckResult()
    object NicknameDuplicatedError: NicknameCheckResult()
}