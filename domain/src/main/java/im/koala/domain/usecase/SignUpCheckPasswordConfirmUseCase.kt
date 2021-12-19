package im.koala.domain.usecase

class SignUpCheckPasswordConfirmUseCase {
    operator fun invoke(password: String, passwordConfirm: String): Boolean =
        password == passwordConfirm
}