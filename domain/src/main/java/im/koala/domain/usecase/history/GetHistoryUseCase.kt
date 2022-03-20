package im.koala.domain.usecase.history

import im.koala.domain.entity.keyword.KeywordNotice
import im.koala.domain.repository.HistoryRepository
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(
        isRead: Boolean? = null
    ): List<KeywordNotice> {
        return if (isRead == null){
            historyRepository.getHistory()
        } else {
            historyRepository.getHistoryByFilter(isRead)
        }
    }
}