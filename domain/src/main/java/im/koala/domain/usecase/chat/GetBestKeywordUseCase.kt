package im.koala.domain.usecase.chat

import im.koala.domain.repository.ChatRepository
import javax.inject.Inject

class GetBestKeywordUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): String {
        return "asdf"
    }
}