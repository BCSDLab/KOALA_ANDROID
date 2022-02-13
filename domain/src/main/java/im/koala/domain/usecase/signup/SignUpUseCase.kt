package im.koala.domain.usecase.signup

import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        accountId: String,
        password: String,
        accountEmail: String,
        accountNickname: String
    ): SignUpResult {
        return userRepository.signUp(accountId, password, accountEmail, accountNickname)
    }
}