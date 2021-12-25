package im.koala.domain.usecase

import javax.inject.Inject

class SignUpCheckPasswordConfirmUseCase @Inject constructor() {
    operator fun invoke(password: String, passwordConfirm: String): Boolean =
        password == passwordConfirm
}