package im.koala.domain.usecase

import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import im.koala.domain.repository.UserRepository
import javax.inject.Inject

class GetKeywordListUseCase@Inject constructor (
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        onSuccess: (MutableList<KeywordResponse>) -> Unit,
        onFail: (CommonResponse) -> Unit
    ) {
        userRepository.getKeyword(
            onSuccess = { onSuccess(it) },
            onFail = { onFail(it) }
        )
    }
}