package im.koala.domain.usecase.keywordAdd

import im.koala.domain.state.NetworkState
import im.koala.domain.model.KeywordAddResponse
import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.repository.UserRepository
import im.koala.domain.usecase.keyword.PushKeywordUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.mock

class PushKeywordUseCaseTest {
    @Mock
    private lateinit var keywordAddRepository: KeywordAddRepository

    @InjectMocks
    private lateinit var pushKeywordUseCase: PushKeywordUseCase

    @Before
    fun init(){
        keywordAddRepository = mock()
        pushKeywordUseCase = PushKeywordUseCase(keywordAddRepository)
    }

    @Test
    fun pushKeywordTest() = runBlockingTest {
        val msg = "msg: 키워드 추가에 성공하였습니다., code: 200"
        val errorMsg = "msg: 키워드 추가에 실패했습니다., code: 400"
        val testResponse = KeywordAddResponse(
            alarmCycle = 240,
            isImportant = 1,
            name = "아우누리",
            silentMode = 1,
            siteList = listOf("PORTAL"),
            untilPressOkButton = 1,
            vibrationMode = 1
        )

        pushKeywordUseCase(testResponse).collectLatest {
            when(it){
                is NetworkState.Success<*> -> {
                    Assert.assertEquals(msg,it.data.toString())
                }
                is NetworkState.Fail<*> -> {
                    Assert.assertEquals(errorMsg,it.data.toString())
                }
                else -> {
                    Assert.assertEquals(errorMsg,it.toString())
                }
            }
        }
    }
}