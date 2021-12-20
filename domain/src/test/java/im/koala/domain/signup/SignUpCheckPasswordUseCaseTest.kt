package im.koala.domain.signup

import im.koala.domain.usecase.SignUpCheckPasswordUseCase
import im.koala.domain.util.checkpassword.PasswordCheckResult
import org.junit.Assert
import org.junit.Test

class SignUpCheckPasswordUseCaseTest {

    val signUpCheckPasswordUseCase = SignUpCheckPasswordUseCase()

    @Test
    fun `비밀번호 공란`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("")[PasswordCheckResult.NoSuchInputError])
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.NoSuchInputError])
    }

    @Test
    fun `정상 비밀번호`() {
        Assert.assertNull(signUpCheckPasswordUseCase("")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("$")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("asdf")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("a1S2d3")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("aAa***")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("*123*")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("heLlo")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("abcdefghijklmnopqrstuvwxyz")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("12345678901234567890")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("$#$^*%&!@#(%&!@#$(&*")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("loremipsumdolorsitamet")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("aaa111sss222ddd333fff444")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("aaa***sss(((ddd)))fff___")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("*1@35^7*9)*1@35^7*9)*1@35^7*9)*")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("asdf🧐fdsa")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("✈️👎🇰🇷TECH👍")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("✈️👎🇰🇷🧑‍🔧👍KOREATECH")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("asdffdsa")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("skybodakoreaTECH")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("SKYBODAKOREATECH")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("*")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1a")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1*")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("a*")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1a*")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("********")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1111aaaa")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1111****")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaa****")[PasswordCheckResult.OK])
        Assert.assertNotNull(signUpCheckPasswordUseCase("111aaa**")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("1111111111111111")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("****************")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111aaaaaaaa")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111********")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa********")[PasswordCheckResult.OK])
        Assert.assertNull(signUpCheckPasswordUseCase("11111aaaaa******")[PasswordCheckResult.OK])

        Assert.assertNotNull(signUpCheckPasswordUseCase("*asdf1234")[PasswordCheckResult.OK])
        Assert.assertNotNull(signUpCheckPasswordUseCase("\$KYB0DAKUT")[PasswordCheckResult.OK])
    }

    @Test
    fun `8자리보다 적은 글자수 입력`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("$")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("asdf")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("a1S2d3")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aAa***")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("*123*")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("heLlo")[PasswordCheckResult.TooShortCharactersError])

        Assert.assertNull(signUpCheckPasswordUseCase("abcdefghijklmnopqrstuvwxyz")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("12345678901234567890")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("$#$^*%&!@#(%&!@#$(&*")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("loremipsumdolorsitamet")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaa111sss222ddd333fff444")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaa***sss(((ddd)))fff___")[PasswordCheckResult.TooShortCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("*1@35^7*9)*1@35^7*9)*1@35^7*9)*")[PasswordCheckResult.TooShortCharactersError])
    }

    @Test
    fun `15자리보다 적은 글자수 입력`() {
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("1")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("$")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("asdf")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("a1S2d3")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("aAa***")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("*123*")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("heLlo")[PasswordCheckResult.TooLongCharactersError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("abcdefghijklmnopqrstuvwxyz")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("12345678901234567890")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("$#$^*%&!@#(%&!@#$(&*")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("loremipsumdolorsitamet")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaa111sss222ddd333fff444")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaa***sss(((ddd)))fff___")[PasswordCheckResult.TooLongCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("*1@35^7*9)*1@35^7*9)*1@35^7*9)*")[PasswordCheckResult.TooLongCharactersError])
    }

    @Test
    fun `이모티콘이 포함된 비밀번호`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("asdf🧐fdsa")[PasswordCheckResult.NotSupportCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("✈️👎🇰🇷TECH👍")[PasswordCheckResult.NotSupportCharactersError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("✈️👎🇰🇷🧑‍🔧👍KOREATECH")[PasswordCheckResult.NotSupportCharactersError])

        Assert.assertNull(signUpCheckPasswordUseCase("asdffdsa")[PasswordCheckResult.NotSupportCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("skybodakoreaTECH")[PasswordCheckResult.NotSupportCharactersError])
        Assert.assertNull(signUpCheckPasswordUseCase("SKYBODAKOREATECH")[PasswordCheckResult.NotSupportCharactersError])
    }

    @Test
    fun `영어가 없는 비밀번호`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("1")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("*")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1*")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("a*")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a*")[PasswordCheckResult.NotContainsEnglishError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("********")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111aaaa")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1111****")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaa****")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("111aaa**")[PasswordCheckResult.NotContainsEnglishError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("1111111111111111")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("****************")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111aaaaaaaa")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111********")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa********")[PasswordCheckResult.NotContainsEnglishError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111aaaaa******")[PasswordCheckResult.NotContainsEnglishError])
    }

    @Test
    fun `숫자가 없는 비밀번호`() {
        Assert.assertNull(signUpCheckPasswordUseCase("1")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("*")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1*")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("a*")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a*")[PasswordCheckResult.NotContainsNumberError])

        Assert.assertNull(signUpCheckPasswordUseCase("11111111")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaa")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("********")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111aaaa")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111****")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaa****")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("111aaa**")[PasswordCheckResult.NotContainsNumberError])

        Assert.assertNull(signUpCheckPasswordUseCase("1111111111111111")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("****************")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111aaaaaaaa")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111********")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaa********")[PasswordCheckResult.NotContainsNumberError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111aaaaa******")[PasswordCheckResult.NotContainsNumberError])
    }

    @Test
    fun `특수문자가 없는 비밀번호`() {
        Assert.assertNotNull(signUpCheckPasswordUseCase("1")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("a")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("*")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1a")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("1*")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("a*")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("1a*")[PasswordCheckResult.NotContainsSpecialCharacterError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaa")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("********")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("1111aaaa")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("1111****")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaa****")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("111aaa**")[PasswordCheckResult.NotContainsSpecialCharacterError])

        Assert.assertNotNull(signUpCheckPasswordUseCase("1111111111111111")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("aaaaaaaaaaaaaaaa")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("****************")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNotNull(signUpCheckPasswordUseCase("11111111aaaaaaaa")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111111********")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("aaaaaaaa********")[PasswordCheckResult.NotContainsSpecialCharacterError])
        Assert.assertNull(signUpCheckPasswordUseCase("11111aaaaa******")[PasswordCheckResult.NotContainsSpecialCharacterError])
    }
}