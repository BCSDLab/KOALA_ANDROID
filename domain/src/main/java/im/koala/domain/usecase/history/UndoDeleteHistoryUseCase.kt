package im.koala.domain.usecase.history

import im.koala.domain.repository.HistoryRepository
import im.koala.domain.state.Result
import javax.inject.Inject

class UndoDeleteHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(noticeId: List<Int>): Result {
        return historyRepository.undoDeleteHistory(noticeId)
    }
}