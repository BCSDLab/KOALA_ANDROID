package im.koala.data.repository

import im.koala.data.source.remote.SignUpRemoteDataSource
import im.koala.domain.entity.signup.SignUpResult
import im.koala.domain.repository.SignUpRepository
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
class SignUpRepositoryTest {

    @Mock
    private lateinit var signUpRemoteDataSource: SignUpRemoteDataSource

    @InjectMocks
    private lateinit var signUpRepository: SignUpRepository

    @Before
    fun init() {
        signUpRemoteDataSource = mock()
        signUpRepository = SignUpRepositoryImpl(signUpRemoteDataSource)
    }

    @Test
    fun `remoteDataSource의 checkIdIsAvailable이 true를 반환할 경우 false를 반환한다`() = runBlockingTest {
        val id = "aaa"
        whenever(signUpRemoteDataSource.checkIdIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(signUpRepository.checkIdDuplicate(id))
    }


    @Test
    fun `remoteDataSource의 checkIdIsAvailable이 false를 반환할 경우 true를 반환한다`() = runBlockingTest {
        val id = "aaa"
        whenever(signUpRemoteDataSource.checkIdIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(signUpRepository.checkIdDuplicate(id))
    }


    @Test
    fun `remoteDataSource의 checkEmailIsAvailable이 true를 반환할 경우 false를 반환한다`() = runBlockingTest {
        val email = "aaa@gmail.com"
        whenever(signUpRemoteDataSource.checkEmailIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(signUpRepository.checkEmailDuplicate(email))
    }


    @Test
    fun `remoteDataSource의 checkEmailIsAvailable이 false를 반환할 경우 true를 반환한다`() = runBlockingTest {
        val email = "aaa@gmail.com"
        whenever(signUpRemoteDataSource.checkEmailIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(signUpRepository.checkEmailDuplicate(email))
    }


    @Test
    fun `remoteDataSource의 checkNicknameIsAvailable이 true를 반환할 경우 false를 반환한다`() = runBlockingTest {
        val nickname = "asdf"
        whenever(signUpRemoteDataSource.checkNicknameIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(signUpRepository.checkNicknameDuplicate(nickname))
    }


    @Test
    fun `remoteDataSource의 checkNicknameIsAvailable이 false를 반환할 경우 true를 반환한다`() = runBlockingTest {
        val nickname = "asdf"
        whenever(signUpRemoteDataSource.checkNicknameIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(signUpRepository.checkNicknameDuplicate(nickname))
    }


    @Test
    fun `signUp이 성공할 경우 SignUpResult_OK가 반환된다`() = runBlockingTest {
        val accountId = "string22"
        val accountEmail = "string@a.cd"
        val accountNickname = "string213"
        val password = "asdf"

        whenever(
            signUpRemoteDataSource.signUp(
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(
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
            signUpRepository.signUp(accountId, password, accountEmail, accountNickname)
        )
    }


    @Test
    fun `signUp이 실패할 경우 SignUpResult_Failed가 반환된다`() = runBlockingTest {
        val accountId = "string22"
        val accountEmail = "string@a.cd"
        val accountNickname = "string213"
        val password = "asdf"

        whenever(
            signUpRemoteDataSource.signUp(
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(
            SignUpResult.Failed("Test")
        )

        Assert.assertEquals(
            SignUpResult.Failed("Test"),
            signUpRepository.signUp(accountId, password, accountEmail, accountNickname)
        )
    }

}