package im.koala.domain.usecase.keywordAdd

import im.koala.bcsd.state.NetworkState
import im.koala.domain.repository.UserRepository
import im.koala.domain.usecase.keyword.GetKeywordSearchUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetKeywordSearchUseCaseTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var getKeywordSearchUseCase: GetKeywordSearchUseCase

    @Before
    fun init(){
        userRepository = mock()
        getKeywordSearchUseCase = GetKeywordSearchUseCase(userRepository)
    }

    @Test
    fun getKeywordSearchUseCaseTest() = runBlockingTest {
        val list = arrayListOf(
            "키워드0",
            "키워드1",
            "키워드2",
            "키워드3",
            "키워드4",
            "키워드5",
            "키워드6",
            "키워드7",
            "키워드8",
            "키워드9",
            "키워드0",
            "키워드0",
            "키워드1",
            "키워드2",
            "키워드3"
        )
        lateinit var result:Any
        whenever(userRepository.getKeywordSearch("키")).thenReturn(NetworkState.Success(list))
        getKeywordSearchUseCase("키").collectLatest {
            result = when(it){
                is NetworkState.Success<*> -> {
                    it.data as MutableList<String>
                }
                is NetworkState.Fail<*> -> {
                    it.data.toString()
                }
                else ->{
                    it.toString()
                }
            }
        }
        Assert.assertEquals(result,list)
    }
}