package im.koala.domain.usecase.chat

import im.koala.domain.repository.ChatRepository
import javax.inject.Inject

class GetNumberOfPeopleUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke() : Int {
        return 24
    }
}