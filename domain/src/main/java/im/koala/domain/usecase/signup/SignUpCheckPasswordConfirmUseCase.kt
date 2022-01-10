package im.koala.domain.usecase.signup

import javax.inject.Inject

class SignUpCheckPasswordConfirmUseCase @Inject constructor() {
    operator fun invoke(password: String, passwordConfirm: String): Boolean =
        password == passwordConfirm
}