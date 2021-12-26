package im.koala.domain.usecase

import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.repository.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(
        accountId: String,
        password: String,
        accountEmail: String,
        accountNickname: String
    ): SignUpResult {
        return signUpRepository.signUp(accountId, password, accountEmail, accountNickname)
    }
}