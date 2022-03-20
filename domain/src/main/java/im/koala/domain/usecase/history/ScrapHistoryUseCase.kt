package im.koala.domain.usecase.history

import im.koala.domain.repository.HistoryRepository
import javax.inject.Inject

class ScrapHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(crawlingIdList: List<Int>): List<Int> {
        return historyRepository.scrapHistory(crawlingIdList)
    }
}