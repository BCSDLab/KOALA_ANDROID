package im.koala.domain.usecase.keywordAdd

import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.state.NetworkState
import im.koala.domain.repository.UserRepository
import im.koala.domain.usecase.keyword.GetSiteSearchUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetSiteSearchUseCaseTest {
    @Mock
    private lateinit var keywordAddRepository: KeywordAddRepository

    @InjectMocks
    private lateinit var getSiteSearchUseCase: GetSiteSearchUseCase

    @Before
    fun init(){
        keywordAddRepository = mock()
        getSiteSearchUseCase = GetSiteSearchUseCase(keywordAddRepository)
    }

    @Test
    fun getSiteSearchUseCaseTest() = runBlockingTest {
        val list = arrayListOf(
            "아우누리",
            "아우미르"
        )
        lateinit var result:Any

        // stub
        whenever(keywordAddRepository.getKeywordSiteSearch("아")).thenReturn(NetworkState.Success(list))

        getSiteSearchUseCase("아").collectLatest {
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