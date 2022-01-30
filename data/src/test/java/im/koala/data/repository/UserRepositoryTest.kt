package im.koala.data.repository

import im.koala.data.repository.local.UserLocalDataSource
import im.koala.data.repository.remote.UserRemoteDataSource
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
class UserRepositoryTest {

    @Mock
    private lateinit var userRemoteDataSource: UserRemoteDataSource
    @Mock
    private lateinit var userLocalDataSource: UserLocalDataSource

    @InjectMocks
    private lateinit var userRepository: UserRepository

    @Before
    fun init() {
        userRemoteDataSource = mock()
        userLocalDataSource = mock()
        userRepository = UserRepositoryImpl(userRemoteDataSource, userLocalDataSource)
    }

    @Test
    fun `remoteDataSource의 checkIdIsAvailable이 true를 반환할 경우 false를 반환한다`() = runBlockingTest {
        val id = "aaa"
        whenever(userRemoteDataSource.checkIdIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(userRepository.checkIdDuplicate(id))
    }

    @Test
    fun `remoteDataSource의 checkIdIsAvailable이 false를 반환할 경우 true를 반환한다`() = runBlockingTest {
        val id = "aaa"
        whenever(userRemoteDataSource.checkIdIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(userRepository.checkIdDuplicate(id))
    }

    @Test
    fun `remoteDataSource의 checkEmailIsAvailable이 true를 반환할 경우 false를 반환한다`() = runBlockingTest {
        val email = "aaa@gmail.com"
        whenever(userRemoteDataSource.checkEmailIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(userRepository.checkEmailDuplicate(email))
    }

    @Test
    fun `remoteDataSource의 checkEmailIsAvailable이 false를 반환할 경우 true를 반환한다`() = runBlockingTest {
        val email = "aaa@gmail.com"
        whenever(userRemoteDataSource.checkEmailIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(userRepository.checkEmailDuplicate(email))
    }

    @Test
    fun `remoteDataSource의 checkNicknameIsAvailable이 true를 반환할 경우 false를 반환한다`() = runBlockingTest {
        val nickname = "asdf"
        whenever(userRemoteDataSource.checkNicknameIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(userRepository.checkNicknameDuplicate(nickname))
    }

    @Test
    fun `remoteDataSource의 checkNicknameIsAvailable이 false를 반환할 경우 true를 반환한다`() = runBlockingTest {
        val nickname = "asdf"
        whenever(userRemoteDataSource.checkNicknameIsAvailable(any()))
            .thenReturn(true)

        Assert.assertFalse(userRepository.checkNicknameDuplicate(nickname))
    }

    @Test
    fun `signUp이 성공할 경우 SignUpResult_OK가 반환된다`() = runBlockingTest {
        val accountId = "string22"
        val accountEmail = "string@a.cd"
        val accountNickname = "string213"
        val password = "asdf"

        whenever(
            userRemoteDataSource.signUp(
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
            userRepository.signUp(accountId, password, accountEmail, accountNickname)
        )
    }

    @Test
    fun `signUp이 실패할 경우 SignUpResult_Failed가 반환된다`() = runBlockingTest {
        val accountId = "string22"
        val accountEmail = "string@a.cd"
        val accountNickname = "string213"
        val password = "asdf"

        whenever(
            userRemoteDataSource.signUp(
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
            userRepository.signUp(accountId, password, accountEmail, accountNickname)
        )
    }
}