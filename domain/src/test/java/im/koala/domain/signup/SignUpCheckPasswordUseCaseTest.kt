package im.koala.domain.signup

import im.koala.domain.usecase.SignUpCheckPasswordUseCase
import im.koala.domain.util.checkpassword.PasswordCheckStatus
import org.junit.Assert
import org.junit.Test

class SignUpCheckPasswordUseCaseTest {

    val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()

    @Test
    fun `비밀번호 공란`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("")[PasswordCheckStatus.NoSuchInputError])
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckStatus.NoSuchInputError])
    }

    @Test
    fun `정상 비밀번호`() {
        Assert.assertTrue(signUpCheckPasswordUseCase("").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("a").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("a").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("1").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("$").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("asdf").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("a1S2d3").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("aAa***").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("*123*").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("heLlo").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("abcdefghijklmnopqrstuvwxyz").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("12345678901234567890").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("$#$^*%&!@#(%&!@#$(&*").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("loremipsumdolorsitamet").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("aaa111sss222ddd333fff444").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("aaa***sss(((ddd)))fff___").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("*1@35^7*9)*1@35^7*9)*1@35^7*9)*").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("asdf🧐fdsa").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("✈️👎🇰🇷TECH👍").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("✈️👎🇰🇷🧑‍🔧👍KOREATECH").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("asdffdsa").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("skybodakoreaTECH").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("SKYBODAKOREATECH").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("1").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("a").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("*").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("1a").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("1*").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("a*").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("1a*").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("11111111").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("aaaaaaaa").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("********").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("1111aaaa").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("1111****").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("aaaa****").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("111aaa**").isOK())
        Assert.assertTrue(signUpCheckPasswordUseCase("1111111111111111").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("****************").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("11111111aaaaaaaa").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("11111111********").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("aaaaaaaa********").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("11111aaaaa******").isError())
        Assert.assertTrue(signUpCheckPasswordUseCase("*asdf1234").isOK())
        Assert.assertTrue(signUpCheckPasswordUseCase("\$KYB0DAKUT").isOK())
    }

    @Test
    fun `8자리보다 적은 글자수 입력`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("a")[PasswordCheckStatus.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1")[PasswordCheckStatus.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("$")[PasswordCheckStatus.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("asdf")[PasswordCheckStatus.TooShortCharactersError])
        Assert.assertTrue(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("a1S2d3"))
        Assert.assertTrue(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("aAa***"))
        Assert.assertTrue(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("*123*"))
        Assert.assertTrue(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("heLlo"))

        Assert.assertNull(signUpCheckPasswordUseCase("abcdefghijklmnopqrstuvwxyz")[PasswordCheckStatus.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("12345678901234567890")[PasswordCheckStatus.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("$#$^*%&!@#(%&!@#$(&*")[PasswordCheckStatus.TooShortCharactersError])
        Assert.assertFalse(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("loremipsumdolorsitamet"))
        Assert.assertFalse(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("aaa111sss222ddd333fff444"))
        Assert.assertFalse(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("aaa***sss(((ddd)))fff___"))
        Assert.assertFalse(PasswordCheckStatus.TooShortCharactersError in signUpCheckPasswordUseCase("*1@35^7*9)*1@35^7*9)*1@35^7*9)*"))
    }

    @Test
    fun `15자리보다 많은 글자수 입력`() {
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckStatus.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("1")[PasswordCheckStatus.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("$")[PasswordCheckStatus.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("asdf")[PasswordCheckStatus.TooLongCharactersError])
        Assert.assertFalse(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("a1S2d3"))
        Assert.assertFalse(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("aAa***"))
        Assert.assertFalse(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("*123*"))
        Assert.assertFalse(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("heLlo"))

        Assert.assertNotNull(signUpCheckPasswordUseCase("abcdefghijklmnopqrstuvwxyz")[PasswordCheckStatus.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("12345678901234567890")[PasswordCheckStatus.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("$#$^*%&!@#(%&!@#$(&*")[PasswordCheckStatus.TooLongCharactersError])
        Assert.assertTrue(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("loremipsumdolorsitamet"))
        Assert.assertTrue(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("aaa111sss222ddd333fff444"))
        Assert.assertTrue(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("aaa***sss(((ddd)))fff___"))
        Assert.assertTrue(PasswordCheckStatus.TooLongCharactersError in signUpCheckPasswordUseCase("*1@35^7*9)*1@35^7*9)*1@35^7*9)*"))
    }

    @Test
    fun `이모티콘이 포함된 비밀번호`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("asdf🧐fdsa")[PasswordCheckStatus.NotSupportCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("✈️👎🇰🇷TECH👍")[PasswordCheckStatus.NotSupportCharactersError])
        Assert.assertTrue(PasswordCheckStatus.NotSupportCharactersError in signUpCheckPasswordUseCase("✈️👎🇰🇷🧑‍🔧👍KOREATECH"))

        Assert.assertNull(signUpCheckPasswordUseCase("asdffdsa")[PasswordCheckStatus.NotSupportCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("skybodakoreaTECH")[PasswordCheckStatus.NotSupportCharactersError])
        Assert.assertFalse(PasswordCheckStatus.NotSupportCharactersError in signUpCheckPasswordUseCase("SKYBODAKOREATECH"))
    }

    @Test
    fun `영어가 없는 비밀번호`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("1")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("*")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1*")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("a*")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a*")[PasswordCheckStatus.NotContainsEnglishError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("********")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111aaaa")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1111****")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaa****")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("111aaa**")[PasswordCheckStatus.NotContainsEnglishError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("1111111111111111")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("****************")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111aaaaaaaa")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111********")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa********")[PasswordCheckStatus.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111aaaaa******")[PasswordCheckStatus.NotContainsEnglishError])
    }

    @Test
    fun `숫자가 없는 비밀번호`() {
        Assert.assertNull(signUpCheckPasswordUseCase("1")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("a")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("*")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1*")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("a*")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a*")[PasswordCheckStatus.NotContainsNumberError])

        Assert.assertNull(signUpCheckPasswordUseCase("11111111")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaa")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("********")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111aaaa")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111****")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaa****")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("111aaa**")[PasswordCheckStatus.NotContainsNumberError])

        Assert.assertNull(signUpCheckPasswordUseCase("1111111111111111")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("****************")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111aaaaaaaa")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111********")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaa********")[PasswordCheckStatus.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111aaaaa******")[PasswordCheckStatus.NotContainsNumberError])
    }

    @Test
    fun `특수문자가 없는 비밀번호`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("1")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("a")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("*")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1a")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("1*")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("a*")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a*")[PasswordCheckStatus.NotContainsSpecialCharacterError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaa")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("********")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1111aaaa")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111****")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaa****")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("111aaa**")[PasswordCheckStatus.NotContainsSpecialCharacterError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("1111111111111111")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("****************")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111aaaaaaaa")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111********")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa********")[PasswordCheckStatus.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111aaaaa******")[PasswordCheckStatus.NotContainsSpecialCharacterError])
    }
}