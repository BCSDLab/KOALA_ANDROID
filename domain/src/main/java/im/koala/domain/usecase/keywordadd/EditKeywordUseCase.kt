package im.koala.domain.usecase.keywordadd

import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.state.Result
import javax.inject.Inject

class EditKeywordUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(
        keyword: String,
        alarmCycle: Int,
        alarmMode: Boolean,
        isImportant: Boolean,
        name: String,
        untilPressOkButton: Boolean,
        vibrationMode: Boolean,
        alarmSiteList: List<String>?
    ): Result =
        keywordAddRepository.editKeyword(
            keyword,
            alarmCycle,
            alarmMode,
            isImportant,
            name,
            untilPressOkButton,
            vibrationMode,
            alarmSiteList
        )
}