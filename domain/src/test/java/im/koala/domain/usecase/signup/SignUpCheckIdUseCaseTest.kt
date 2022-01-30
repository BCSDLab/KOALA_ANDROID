package im.koala.domain.usecase.signup

import im.koala.domain.repository.UserRepository
import im.koala.domain.util.checkid.IdCheckResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
class SignUpCheckIdUseCaseTest {

    @Mock
    private lateinit var signUpRepository: UserRepository

    @InjectMocks
    private lateinit var signUpCheckIdUseCase: SignUpCheckIdUseCase

    @Before
    fun init() {
        signUpRepository = mock()
        signUpCheckIdUseCase = SignUpCheckIdUseCase(signUpRepository)
    }

    @Test
    fun `id가 공백 문자이면 IdCheckResult_NoSuchInputError가 반환된다`() = runBlockingTest {
        val ids = listOf(
            "",
            " ",
            "   "
        )
        ids.forEach {
            Assert.assertEquals(
                IdCheckResult.NoSuchInputError,
                signUpCheckIdUseCase(it)
            )
        }
    }

    @Test
    fun `id가 중복되면 IdCheckResult_IdDuplicatedError가 반환된다`() = runBlockingTest {
        val id = "SKYBODAKOREATECH"

        whenever(signUpRepository.checkIdDuplicate(any())).thenReturn(true)

        Assert.assertEquals(
            IdCheckResult.IdDuplicatedError,
            signUpCheckIdUseCase(id)
        )
    }

    @Test
    fun `id 조건이 모두 충족되면 IdCheckResult_OK가 반환된다`() = runBlockingTest {
        val id = "SKYBODAKOREATECH"

        whenever(signUpRepository.checkIdDuplicate(any())).thenReturn(false)

        Assert.assertEquals(
            IdCheckResult.OK,
            signUpCheckIdUseCase(id)
        )
    }
}