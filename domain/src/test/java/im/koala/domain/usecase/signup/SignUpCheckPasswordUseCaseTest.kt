package im.koala.domain.entity.signup

import im.koala.domain.usecase.signup.SignUpCheckPasswordUseCase
import im.koala.domain.util.checkpassword.PasswordCheckStatus
import org.junit.Assert
import org.junit.Test

class SignUpCheckPasswordUseCaseTest {

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertFalse(passwordCheckResult.isOK())
    }

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult에 NoSuchInputError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertTrue(PasswordCheckStatus.NoSuchInputError in passwordCheckResult)
    }

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult에 TooShortCharactersError 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertTrue(PasswordCheckStatus.TooShortCharactersError in passwordCheckResult)
    }

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult에 TooLongCharactersError 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertFalse(PasswordCheckStatus.TooLongCharactersError in passwordCheckResult)
    }

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult에 NotContainsEnglishError 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertTrue(PasswordCheckStatus.NotContainsEnglishError in passwordCheckResult)
    }

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult에 NotContainsNumberError 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertTrue(PasswordCheckStatus.NotContainsNumberError in passwordCheckResult)
    }

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult에 NotContainsSpecialCharacterError 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertTrue(PasswordCheckStatus.NotContainsSpecialCharacterError in passwordCheckResult)
    }

    @Test
    fun `비밀번호가 공란이면 PasswordCheckResult에 NotSupportCharactersError 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val password = ""

        val passwordCheckResult = signUpCheckPasswordUseCase(password)

        Assert.assertFalse(PasswordCheckStatus.NotSupportCharactersError in passwordCheckResult)
    }

    @Test
    fun `비밀번호 글자 수가 8자 이하이면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "a",
            "aa",
            "aaa",
            "aaaa",
            "aaaaa",
            "aaaaaa",
            "aaaaaaa",
            "1",
            "11",
            "111",
            "1111",
            "11111",
            "111111",
            "1111111",
            "*",
            "**",
            "***",
            "****",
            "*****",
            "******",
            "*******",
            "a",
            "a1",
            "aa1",
            "aa11",
            "aa111",
            "a*aa11",
            "a**a111",
            "1",
            "1*",
            "1*1",
            "1**1",
            "1**11",
            "11***1",
            "1*aa*11",
            "*a",
            "*a",
            "*a*",
            "*aa*",
            "***a*",
            "**a***",
            "***a***"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호 글자 수가 8자 이하이면 PasswordCheckResult가 TooShortCharactersError여야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "a",
            "aa",
            "aaa",
            "aaaa",
            "aaaaa",
            "aaaaaa",
            "aaaaaaa",
            "1",
            "11",
            "111",
            "1111",
            "11111",
            "111111",
            "1111111",
            "*",
            "**",
            "***",
            "****",
            "*****",
            "******",
            "*******",
            "a",
            "a1",
            "aa1",
            "aa11",
            "aa111",
            "a*aa11",
            "a**a111",
            "1",
            "1*",
            "1*1",
            "1**1",
            "1**11",
            "11***1",
            "1*aa*11",
            "*a",
            "*a",
            "*a*",
            "*aa*",
            "***a*",
            "**a***",
            "***a***"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.TooShortCharactersError in it)
        }
    }

    @Test
    fun `비밀번호 글자 수가 8자 이하이면 PasswordCheckResult가 TooLongCharactersError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "a",
            "aa",
            "aaa",
            "aaaa",
            "aaaaa",
            "aaaaaa",
            "aaaaaaa",
            "1",
            "11",
            "111",
            "1111",
            "11111",
            "111111",
            "1111111",
            "*",
            "**",
            "***",
            "****",
            "*****",
            "******",
            "*******",
            "a",
            "a1",
            "aa1",
            "aa11",
            "aa111",
            "a*aa11",
            "a**a111",
            "1",
            "1*",
            "1*1",
            "1**1",
            "1**11",
            "11***1",
            "1*aa*11",
            "*a",
            "*a",
            "*a*",
            "*aa*",
            "***a*",
            "**a***",
            "***a***"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.TooLongCharactersError in it)
        }
    }

    @Test
    fun `비밀번호 글자 수가 15자 이상이면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdfjklhasdflkjhadfl",
            "3120451230957132098713",
            "(@&\$#^(@&\$#^(@&\$#^(@&\$#^(@&\$#^",
            "1234lksdflkh9dsf",
            "%^&(*akjlsd*^kjlsd*^",
            "*&(56789%&^(*(56789%&^(*",
            "asdf^(*&234576254"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호 글자 수가 15자 이상이면 PasswordCheckResult가 NoSuchInputError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdfjklhasdflkjhadfl",
            "3120451230957132098713",
            "(@&\$#^(@&\$#^(@&\$#^(@&\$#^(@&\$#^",
            "1234lksdflkh9dsf",
            "%^&(*akjlsd*^kjlsd*^",
            "*&(56789%&^(*(56789%&^(*",
            "asdf^(*&234576254"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.NoSuchInputError in it)
        }
    }

    @Test
    fun `비밀번호 글자 수가 15자 이상이면 PasswordCheckResult가 TooShortCharactersError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdfjklhasdflkjhadfl",
            "3120451230957132098713",
            "(@&\$#^(@&\$#^(@&\$#^(@&\$#^(@&\$#^",
            "1234lksdflkh9dsf",
            "%^&(*akjlsd*^kjlsd*^",
            "*&(56789%&^(*(56789%&^(*",
            "asdf^(*&234576254"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.TooShortCharactersError in it)
        }
    }

    @Test
    fun `비밀번호 글자 수가 15자 이상이면 PasswordCheckResult가 TooLongCharactersError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdfjklhasdflkjhadfl",
            "3120451230957132098713",
            "(@&\$#^(@&\$#^(@&\$#^(@&\$#^(@&\$#^",
            "1234lksdflkh9dsf",
            "%^&(*akjlsd*^kjlsd*^",
            "*&(56789%&^(*(56789%&^(*",
            "asdf^(*&234576254"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.TooLongCharactersError in it)
        }
    }

    @Test
    fun `비밀번호에 영어가 포함되어 있지 않으면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "123415151341243145",
            "1235123423",
            "1234",
            "&)(&()&()(&)&(&()",
            "%&*)",
            "&)&**)*&&",
            "12*&(",
            "12340976^*",
            "23409524356%&*(&%*^("
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호에 영어가 포함되어 있지 않으면 PasswordCheckResult가 NoSuchInputError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "123415151341243145",
            "1235123423",
            "1234",
            "&)(&()&()(&)&(&()",
            "%&*)",
            "&)&**)*&&",
            "12*&(",
            "12340976^*",
            "23409524356%&*(&%*^("
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.NoSuchInputError in it)
        }
    }

    @Test
    fun `비밀번호에 영어가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsEnglishError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "123415151341243145",
            "1235123423",
            "1234",
            "&)(&()&()(&)&(&()",
            "%&*)",
            "&)&**)*&&",
            "12*&(",
            "12340976^*",
            "23409524356%&*(&%*^("
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsEnglishError in it)
        }
    }

    @Test
    fun `비밀번호에 숫자가 포함되어 있지 않으면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfasdf",
            "asdfasdfasdfasdfasdf",
            "as*(",
            "asdfasdf&*()",
            "asdfasdfasdf&*()&()",
            "&*()",
            "&*()&*()&*()",
            "&*()&*()*&()&*()&*()"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호에 숫자가 포함되어 있지 않으면 PasswordCheckResult가 NoSuchInputError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfasdf",
            "asdfasdfasdfasdfasdf",
            "as*(",
            "asdfasdf&*()",
            "asdfasdfasdf&*()&()",
            "&*()",
            "&*()&*()&*()",
            "&*()&*()*&()&*()&*()"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.NoSuchInputError in it)
        }
    }

    @Test
    fun `비밀번호에 숫자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsNumberError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfasdf",
            "asdfasdfasdfasdfasdf",
            "as*(",
            "asdfasdf&*()",
            "asdfasdfasdf&*()&()",
            "&*()",
            "&*()&*()&*()",
            "&*()&*()*&()&*()&*()"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsNumberError in it)
        }
    }

    @Test
    fun `비밀번호에 특수문가 포함되어 있지 않으면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfassfd",
            "asdfasdfasdfasdfasdf",
            "1234",
            "12345876",
            "1293487612349876",
            "as12",
            "asdf1234asdf",
            "asd7f654asdf7654"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호에 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NoSuchInputError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfassfd",
            "asdfasdfasdfasdfasdf",
            "1234",
            "12345876",
            "1293487612349876",
            "as12",
            "asdf1234asdf",
            "asd7f654asdf7654"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.NoSuchInputError in it)
        }
    }

    @Test
    fun `비밀번호에 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsSpecialCharacterError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfassfd",
            "asdfasdfasdfasdfasdf",
            "1234",
            "12345876",
            "1293487612349876",
            "as12",
            "asdf1234asdf",
            "asd7f654asdf7654"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsSpecialCharacterError in it)
        }
    }

    @Test
    fun `비밀번호에 영어와 숫자가 포함되어 있지 않으면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "&*()",
            "&*()&(*)&*()",
            "&*()&*()&*()&*()&*()*&()"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호에 영어와 숫자가 포함되어 있지 않으면 PasswordCheckResult가 NoSuchInputError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "&*()",
            "&*()&(*)&*()",
            "&*()&*()&*()&*()&*()*&()"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.NoSuchInputError in it)
        }
    }

    @Test
    fun `비밀번호에 영어와 숫자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsEnglishError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "&*()",
            "&*()&(*)&*()",
            "&*()&*()&*()&*()&*()*&()"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsEnglishError in it)
        }
    }

    @Test
    fun `비밀번호에 영어와 숫자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsNumberError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "&*()",
            "&*()&(*)&*()",
            "&*()&*()&*()&*()&*()*&()"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsNumberError in it)
        }
    }

    @Test
    fun `비밀번호에 영어와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "1234",
            "1234123412",
            "123412341234124213"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호에 영어와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NoSuchInputError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "1234",
            "1234123412",
            "123412341234124213"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.NoSuchInputError in it)
        }
    }

    @Test
    fun `비밀번호에 영어와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsEnglishError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "1234",
            "1234123412",
            "123412341234124213"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsEnglishError in it)
        }
    }

    @Test
    fun `비밀번호에 영어와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsSpecialCharacterError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "1234",
            "1234123412",
            "123412341234124213"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsSpecialCharacterError in it)
        }
    }

    @Test
    fun `비밀번호에 숫자와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 OK가 아니어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfasdf",
            "asdfasdfasdfasdfasdf"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(it.isOK())
        }
    }

    @Test
    fun `비밀번호에 숫자와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NoSuchInputError가 포함되어 있지 않아야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfasdf",
            "asdfasdfasdfasdfasdf"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertFalse(PasswordCheckStatus.NoSuchInputError in it)
        }
    }

    @Test
    fun `비밀번호에 숫자와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsNumberError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfasdf",
            "asdfasdfasdfasdfasdf"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsNumberError in it)
        }
    }

    @Test
    fun `비밀번호에 숫자와 특수문자가 포함되어 있지 않으면 PasswordCheckResult가 NotContainsSpecialCharacterError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "asdf",
            "asdfasdfasdf",
            "asdfasdfasdfasdfasdf"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotContainsSpecialCharacterError in it)
        }
    }

    @Test
    fun `비밀번호에 이모티콘이 있으면 PasswordCheckResult가 NotSupportCharactersError가 포함되어 있어야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "✈️boda🇰🇷🧑‍🔧",
            "😫"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(PasswordCheckStatus.NotSupportCharactersError in it)
        }
    }

    @Test
    fun `Koala 비밀번호 조건에 맞는 비밀번호는 PasswordCheckResult가 OK여야 한다`() {
        val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()
        val passwords = listOf(
            "a1s2d3f4*",
            "*rnv123lkd0",
            "\$KYB0DAKUT",
            "BC\$DANDR0ID"
        )

        val passwordCheckResults = passwords.map { signUpCheckPasswordUseCase(it) }

        passwordCheckResults.map {
            Assert.assertTrue(it.isOK())
        }
    }
}