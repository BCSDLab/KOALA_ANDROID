package im.koala.domain.usecase.keyword

import im.koala.bcsd.state.NetworkState
import im.koala.domain.model.KeywordAddResponse
import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PushKeywordUseCase @Inject constructor (
    private val userRepository: UserRepository
) {
    operator fun invoke(keywordResponse: KeywordAddResponse): Flow<NetworkState> = flow {
        emit(NetworkState.Loading)
        var result = userRepository.pushKeyword(keywordResponse)
        emit(result)
    }
}