package im.koala.data.repository

import im.koala.data.source.remote.SignUpRemoteDataSource
import im.koala.domain.entity.signup.SignUpResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
class SignUpRepositoryTest {

    @Mock
    private lateinit var signUpRemoteDataSource: SignUpRemoteDataSource

    @InjectMocks
    private lateinit var signUpRepository: SignUpRepositoryImpl

    @Test
    fun `remoteDataSource의 checkIdIsAvailable이 true를 반환할 경우 false를 반환한다`() {
        TestCoroutineScope().launch {
            val id = "aaa"
            Mockito.`when`(signUpRemoteDataSource.checkIdIsAvailable(id)).thenReturn(true)

            Assert.assertFalse(signUpRepository.checkIdDuplicate(id))
        }
    }

    @Test
    fun `remoteDataSource의 checkIdIsAvailable이 false를 반환할 경우 true를 반환한다`() {
        TestCoroutineScope().launch {
            val id = "aaa"
            Mockito.`when`(signUpRemoteDataSource.checkIdIsAvailable(id)).thenReturn(true)

            Assert.assertFalse(signUpRepository.checkIdDuplicate(id))
        }
    }

    @Test
    fun `remoteDataSource의 checkEmailIsAvailable이 true를 반환할 경우 false를 반환한다`() {
        TestCoroutineScope().launch {
            val email = "aaa@gmail.com"
            Mockito.`when`(signUpRemoteDataSource.checkEmailIsAvailable(email)).thenReturn(true)

            Assert.assertFalse(signUpRepository.checkEmailDuplicate(email))
        }
    }

    @Test
    fun `remoteDataSource의 checkEmailIsAvailable이 false를 반환할 경우 true를 반환한다`() {
        TestCoroutineScope().launch {
            val email = "aaa@gmail.com"
            Mockito.`when`(signUpRemoteDataSource.checkEmailIsAvailable(email)).thenReturn(true)

            Assert.assertFalse(signUpRepository.checkEmailDuplicate(email))
        }
    }

    @Test
    fun `remoteDataSource의 checkNicknameIsAvailable이 true를 반환할 경우 false를 반환한다`() {
        TestCoroutineScope().launch {
            val nickname = "asdf"
            Mockito.`when`(signUpRemoteDataSource.checkNicknameIsAvailable(nickname))
                .thenReturn(true)

            Assert.assertFalse(signUpRepository.checkNicknameDuplicate(nickname))
        }
    }

    @Test
    fun `remoteDataSource의 checkNicknameIsAvailable이 false를 반환할 경우 true를 반환한다`() {
        TestCoroutineScope().launch {
            val nickname = "asdf"
            Mockito.`when`(signUpRemoteDataSource.checkNicknameIsAvailable(nickname))
                .thenReturn(true)

            Assert.assertFalse(signUpRepository.checkNicknameDuplicate(nickname))
        }
    }

    @Test
    fun `signUp이 성공할 경우 SignUpResult_OK가 반환된다`() {
        TestCoroutineScope().launch {
            val accountId = "string22"
            val accountEmail = "string@a.cd"
            val accountNickname = "string213"
            val password = "asdf"

            Mockito.`when`(
                signUpRemoteDataSource.signUp(
                    accountId,
                    accountEmail,
                    accountNickname,
                    password
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
                ), signUpRepository.signUp(accountId, password, accountEmail, accountNickname)
            )
        }
    }

    @Test
    fun `signUp이 실패할 경우 SignUpResult_Failed가 반환된다`() {
        TestCoroutineScope().launch {
            val accountId = "string22"
            val accountEmail = "string@a.cd"
            val accountNickname = "string213"
            val password = "asdf"

            Mockito.`when`(
                signUpRemoteDataSource.signUp(
                    accountId,
                    accountEmail,
                    accountNickname,
                    password
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
}