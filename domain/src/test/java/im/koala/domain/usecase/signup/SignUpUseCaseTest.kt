package im.koala.domain.usecase.signup

import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.repository.UserRepository
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
class SignUpUseCaseTest {

    @Mock
    private lateinit var signUpRepository: UserRepository

    @InjectMocks
    private lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun init() {
        signUpRepository = mock()
        signUpUseCase = SignUpUseCase(signUpRepository)
    }

    @Test
    fun `signup에 성공할 경우 SignupResult_OK 반환`() = runBlockingTest {
        val accountId = "string22"
        val accountEmail = "string@a.cd"
        val accountNickname = "string213"
        val password = "asdf"

        whenever(signUpRepository.signUp(any(), any(), any(), any())).thenReturn(
            SignUpResult.OK(
                userId = 71,
                accountId = accountId,
                accountEmail = accountEmail,
                accountNickname = accountNickname,
                userType = 0,
                isAuth = 0
            )
        )

        Assert.assertEquals(
            SignUpResult.OK(
                userId = 71,
                accountId = accountId,
                accountEmail = accountEmail,
                accountNickname = accountNickname,
                userType = 0,
                isAuth = 0
            ),
            signUpUseCase(accountId, password, accountEmail, accountNickname)
        )
    }

    @Test
    fun `signup에 실패할 경우 SignupResult_Failed 반환`() = runBlockingTest {
        val accountId = "string22"
        val accountEmail = "string@a.cd"
        val accountNickname = "string213"
        val password = "asdf"

        whenever(signUpRepository.signUp(any(), any(), any(), any())).thenReturn(
            SignUpResult.Failed("Test")
        )

        Assert.assertEquals(
            SignUpResult.Failed("Test"),
            signUpUseCase(accountId, password, accountEmail, accountNickname)
        )
    }
}