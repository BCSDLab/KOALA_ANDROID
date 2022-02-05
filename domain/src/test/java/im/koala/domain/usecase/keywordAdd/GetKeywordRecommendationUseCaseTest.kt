package im.koala.domain.usecase.keywordAdd

import im.koala.bcsd.state.NetworkState
import im.koala.domain.repository.UserRepository
import im.koala.domain.usecase.keyword.GetKeywordRecommendationUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.mock

class GetKeywordRecommendationUseCaseTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var getKeywordRecommendationUseCase: GetKeywordRecommendationUseCase

    @Before
    fun init(){
        userRepository = mock()
        getKeywordRecommendationUseCase = GetKeywordRecommendationUseCase(userRepository)
    }

    @Test
    fun getKeywordRecommendationTest() = runBlockingTest {
        val list = arrayListOf(
            "키워드0",
            "키워드1",
            "키워드2",
            "키워드3",
            "string",
            "키워드4",
            "키워드5",
            "키워드6",
            "키워드7",
            "키워드8"
        )
        getKeywordRecommendationUseCase().collectLatest {
            when(it){
                is NetworkState.Success<*> -> {
                    Assert.assertEquals(list,it.data as MutableList<String>)
                }
            }
        }
    }
}