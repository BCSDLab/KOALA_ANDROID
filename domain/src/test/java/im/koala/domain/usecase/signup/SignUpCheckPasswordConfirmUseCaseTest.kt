package im.koala.domain.usecase.signup

import org.junit.Assert
import org.junit.Test

class SignUpCheckPasswordConfirmUseCaseTest {

    private val signUpCheckPasswordConfirmUseCase = SignUpCheckPasswordConfirmUseCase()

    @Test
    fun `비밀번호가 일치할 경우 true를 반환`() {
        Assert.assertTrue(
            signUpCheckPasswordConfirmUseCase(
                "abcd",
                "abcd"
            )
        )
    }

    @Test
    fun `비밀번호가 일치하지 않을 경우 false를 반환`() {
        Assert.assertFalse(
            signUpCheckPasswordConfirmUseCase(
                "abcd",
                "abcd2"
            )
        )
    }
}