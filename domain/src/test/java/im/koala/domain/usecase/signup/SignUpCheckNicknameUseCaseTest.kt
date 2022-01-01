package im.koala.domain.usecase.signup

import im.koala.domain.repository.SignUpRepository
import im.koala.domain.util.checknickname.NicknameCheckResult
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
class SignUpCheckNicknameUseCaseTest {

    @Mock
    private lateinit var signUpRepository: SignUpRepository

    @InjectMocks
    private lateinit var signUpCheckNicknameUseCase: SignUpCheckNicknameUseCase

    @Before
    fun init() {
        signUpRepository = mock()
        signUpCheckNicknameUseCase = SignUpCheckNicknameUseCase(signUpRepository)
    }

    @Test
    fun `nickname이 공백 문자이면 NicknameCheckResult_NoSuchInputError가 반환된다`() = runBlockingTest {
        val nicknames = listOf(
            "",
            " ",
            "   "
        )
        nicknames.forEach {
            Assert.assertEquals(
                NicknameCheckResult.NoSuchInputError,
                signUpCheckNicknameUseCase(it)
            )
        }
    }


    @Test
    fun `nickname이 중복되면 NicknameCheckResult_IdDuplicatedError가 반환된다`() = runBlockingTest {
        val nickname = "SKYBODAKOREATECH"

        whenever(signUpRepository.checkNicknameDuplicate(any())).thenReturn(true)

        Assert.assertEquals(
            NicknameCheckResult.NicknameDuplicatedError,
            signUpCheckNicknameUseCase(nickname)
        )

    }


    @Test
    fun `nickname 조건이 모두 충족되면 NicknameCheckResult_OK가 반환된다`() = runBlockingTest {
        val nickname = "SKYBODAKOREATECH"

        whenever(signUpRepository.checkNicknameDuplicate(any())).thenReturn(false)

        Assert.assertEquals(
            NicknameCheckResult.OK,
            signUpCheckNicknameUseCase(nickname)
        )

    }

}