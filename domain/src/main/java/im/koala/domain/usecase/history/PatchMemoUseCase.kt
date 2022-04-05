package im.koala.domain.usecase.history

import im.koala.domain.repository.HistoryRepository
import im.koala.domain.state.Result
import javax.inject.Inject

class PatchMemoUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(userScrapId: Int, memo: String): Result {
        return historyRepository.patchMemo(userScrapId, memo)
    }
}