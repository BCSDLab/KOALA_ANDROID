package im.koala.domain.usecase

import im.koala.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    operator fun invoke(
        id: String,
        password: String,
        email: String,
        nickname: String
    ): Boolean {
        return signUpRepository.signUp(id, password, email, nickname)
    }
}