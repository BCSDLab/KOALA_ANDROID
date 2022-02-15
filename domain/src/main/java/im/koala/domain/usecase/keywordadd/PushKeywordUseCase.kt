package im.koala.domain.usecase.keywordadd

import im.koala.domain.state.Result
import im.koala.domain.repository.KeywordAddRepository
import javax.inject.Inject

class PushKeywordUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(
        alarmCycle: Int,
        alarmMode: Boolean,
        isImportant: Boolean,
        name: String,
        untilPressOkButton: Boolean,
        vibrationMode: Boolean,
        alarmSiteList: List<String>?
    ): Result =
        keywordAddRepository.pushKeyword(
            alarmCycle,
            alarmMode,
            isImportant,
            name,
            untilPressOkButton,
            vibrationMode,
            alarmSiteList
        )
}