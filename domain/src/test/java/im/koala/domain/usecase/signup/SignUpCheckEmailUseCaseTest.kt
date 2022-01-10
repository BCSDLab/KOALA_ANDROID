package im.koala.domain.usecase.signup

import im.koala.domain.repository.SignUpRepository
import im.koala.domain.util.checkemail.EmailCheckResult
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
class SignUpCheckEmailUseCaseTest {

    @Mock
    private lateinit var signUpRepository: SignUpRepository

    @InjectMocks
    private lateinit var signUpCheckEmailUseCase: SignUpCheckEmailUseCase

    @Before
    fun init() {
        signUpRepository = mock()
        signUpCheckEmailUseCase = SignUpCheckEmailUseCase(signUpRepository)
    }

    @Test
    fun `이메일이 공백 문자이면 EmailCheckResult_NoSuchInputError가 반환된다`() = runBlockingTest {
        val emails = listOf(
            "",
            " ",
            "   "
        )
        emails.forEach {
            Assert.assertEquals(
                EmailCheckResult.NoSuchInputError,
                signUpCheckEmailUseCase(it)
            )
        }
    }

    @Test
    fun `이메일이 이메일 형식이 아닐 경우 EmailCheckResult_NotEmailFormatError가 반환된다`() = runBlockingTest {
        val emails = listOf(
            "@.",
            "asdf",
            "a@",
            "aaa@.",
            "asdf@fff.",
            "as@a.a.",
            "skybodakoreatech@gmail.com."
        )
        emails.forEach {
            Assert.assertEquals(
                EmailCheckResult.NotEmailFormatError,
                signUpCheckEmailUseCase(it)
            )
        }
    }

    @Test
    fun `이메일이 중복될 경우 EmailCheckResult_NotEmailFormatError가 반환된다`() = runBlockingTest {
        val email = "skyboda@koreatech.ac.kr"

        whenever(signUpRepository.checkEmailDuplicate(any())).thenReturn(true)

        Assert.assertEquals(
            EmailCheckResult.EmailDuplicatedError,
            signUpCheckEmailUseCase(email)
        )
    }

    @Test
    fun `이메일 조건을 모두 충족하면 EmailCheckResult_OK가 반환된다`() = runBlockingTest {
        val email = "skyboda@koreatech.ac.kr"

        whenever(signUpRepository.checkEmailDuplicate(any())).thenReturn(false)

        Assert.assertEquals(
            EmailCheckResult.OK,
            signUpCheckEmailUseCase(email)
        )
    }
}