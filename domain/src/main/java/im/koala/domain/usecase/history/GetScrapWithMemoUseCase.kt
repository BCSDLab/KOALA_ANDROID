package im.koala.domain.usecase.history

import im.koala.domain.entity.history.ScrapNotice
import im.koala.domain.repository.HistoryRepository
import javax.inject.Inject

class GetScrapWithMemoUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(): List<ScrapNotice> {
        return historyRepository.getScrapWithMemo()
    }
}