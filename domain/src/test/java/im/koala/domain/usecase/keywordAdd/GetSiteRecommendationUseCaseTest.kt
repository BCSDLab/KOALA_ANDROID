package im.koala.domain.usecase.keywordAdd

import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.usecase.keyword.GetSiteRecommendationUseCase
import im.koala.domain.state.NetworkState
import im.koala.domain.repository.UserRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetSiteRecommendationUseCaseTest {
    @Mock
    private lateinit var keywordAddRepository: KeywordAddRepository

    @InjectMocks
    private lateinit var getSiteRecommendationUseCase: GetSiteRecommendationUseCase

    @Before
    fun init(){
        keywordAddRepository = mock()
        getSiteRecommendationUseCase = GetSiteRecommendationUseCase(keywordAddRepository)
    }

    @Test
    fun getSiteRecommendationUseCaseTest() = runBlockingTest {
        val list = arrayListOf(
            "아우누리",
            "아우미르",
            "페이스북",
            "한국기술교육대학교 유튜브",
            "인스타그램"
        )
        lateinit var result:Any
        whenever(keywordAddRepository.getKeywordSiteRecommendation()).thenReturn(NetworkState.Success(list))
        getSiteRecommendationUseCase().collectLatest {
            result = when(it){
                is NetworkState.Success<*> -> {
                    it.data as MutableList<String>
                }
                is NetworkState.Fail<*> -> {
                   it.data.toString()
                }
                else -> {
                    it.toString()
                }
            }
        }
        Assert.assertEquals(result,list)
    }
}