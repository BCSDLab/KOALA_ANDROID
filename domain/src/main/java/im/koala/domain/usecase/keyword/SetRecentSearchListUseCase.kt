package im.koala.domain.usecase.keyword

import im.koala.domain.repository.KeywordAddRepository
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class SetRecentSearchListUseCase @Inject constructor(
    private val keywordAddRepository: KeywordAddRepository
) {
    suspend operator fun invoke(key:String,searchList:List<String>){
        keywordAddRepository.setRecentSearchList(key,searchList)
    }
}