package im.koala.data.source.remote

import com.google.gson.Gson
import com.google.gson.JsonParseException
import im.koala.data.api.NoAuthApi
import im.koala.data.api.request.signup.SignUpRequest
import im.koala.data.api.response.signup.SignUpResultResponse
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE
import im.koala.domain.entity.signup.SignUpResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
class SignUpRemoteDataSourceTest {

    @Mock
    private lateinit var noAuthApi: NoAuthApi

    @InjectMocks
    private lateinit var signUpRemoteDataSource: SignUpRemoteDataSource

    @Test
    fun `API의 checkAccount에서 존재하지 않는 Account를 반환할 경우 checkIdIsAvailable이 true를 반환`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkAccount(id)).thenThrow(
                HttpException(
                    Response.error<String>(
                        400,
                        """
                        {
                          "className": "NonCriticalException",
                          "errorMessage": "존재하지 않는 아이디입니다.",
                          "code": 126,
                          "errorTrace": "in.koala.serviceImpl.UserServiceImpl.checkAccount(UserServiceImpl.java:377)"
                        }
                    """.trimIndent().toResponseBody()
                    )
                )
            )

            Assert.assertTrue(signUpRemoteDataSource.checkIdIsAvailable(id))
        }
    }

    @Test
    fun `API의 checkAccount에서 존재하는 Account를 반환할 경우 checkIdIsAvailable이 false를 반환`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkAccount(id)).thenReturn(
                Response.success(KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE)
            )

            Assert.assertFalse(signUpRemoteDataSource.checkIdIsAvailable(id))
        }
    }

    @Test
    fun `API의 checkAccount에서 예외가 발생할 경우 checkIdIsAvailable이 그대로 예외 전달`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkAccount(id)).thenThrow(
                JsonParseException("")
            )

            Assert.assertThrows(JsonParseException::class.java) {
                TestCoroutineScope().launch {
                    signUpRemoteDataSource.checkIdIsAvailable(id)
                }
            }
        }
    }

    @Test
    fun `API의 checkEmail에서 존재하지 않는 Account를 반환할 경우 checkNicknameIsAvailable이 true를 반환`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkEmail(id)).thenReturn(
                Response.success(KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE)
            )

            Assert.assertTrue(signUpRemoteDataSource.checkIdIsAvailable(id))
        }
    }

    @Test
    fun `API의 checkEmail에서 존재하는 Account를 반환할 경우 checkNicknameIsAvailable이 false를 반환`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkEmail(id)).thenThrow(
                HttpException(
                    Response.error<String>(
                        400,
                        """ {
                              "className": "NonCriticalException",
                              "errorMessage": "이미 존재하는 이메일입니다",
                              "code": 125,
                              "errorTrace": "in.koala.serviceImpl.UserServiceImpl.checkFindEmail(UserServiceImpl.java:153)"
                            }
                    """.trimIndent().toResponseBody()
                    )
                )
            )

            Assert.assertFalse(signUpRemoteDataSource.checkIdIsAvailable(id))
        }
    }

    @Test
    fun `API의 checkEmail에서 예외가 발생할 경우 checkNicknameIsAvailable이 그대로 예외 전달`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkEmail(id)).thenThrow(
                JsonParseException("")
            )

            Assert.assertThrows(JsonParseException::class.java) {
                TestCoroutineScope().launch {
                    signUpRemoteDataSource.checkIdIsAvailable(id)
                }
            }
        }
    }

    @Test
    fun `API의 checkNickname에서 존재하지 않는 Account를 반환할 경우 checkNicknameIsAvailable이 true를 반환`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkNickname(id)).thenReturn(
                Response.success(KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE)
            )

            Assert.assertTrue(signUpRemoteDataSource.checkIdIsAvailable(id))
        }
    }

    @Test
    fun `API의 checkNickname에서 존재하는 Account를 반환할 경우 checkNicknameIsAvailable이 false를 반환`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkNickname(id)).thenThrow(
                HttpException(
                    Response.error<String>(
                        400,
                        """
                            {
                              "className": "NonCriticalException",
                              "errorMessage": "이미 존재하는 닉네임입니다",
                              "code": 124,
                              "errorTrace": "in.koala.serviceImpl.UserServiceImpl.checkNickname(UserServiceImpl.java:223)"
                            }
                    """.trimIndent().toResponseBody()
                    )
                )
            )

            Assert.assertFalse(signUpRemoteDataSource.checkIdIsAvailable(id))
        }
    }

    @Test
    fun `API의 checkNickname에서 예외가 발생할 경우 checkNicknameIsAvailable이 그대로 예외 전달`() {
        TestCoroutineScope().launch {
            val id = "abc"
            Mockito.`when`(noAuthApi.checkNickname(id)).thenThrow(
                JsonParseException("")
            )

            Assert.assertThrows(JsonParseException::class.java) {
                TestCoroutineScope().launch {
                    signUpRemoteDataSource.checkIdIsAvailable(id)
                }
            }
        }
    }

    @Test
    fun `API의 signUp에서 이메일 형식에 맞지 않는 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() {
        TestCoroutineScope().launch {
            val accountId = "aaa"
            val accountEmail = "asdf"
            val accountNickname = "asdf"
            val password = "asdf"

            Mockito.`when`(
                noAuthApi.signUp(
                    SignUpRequest(
                        account = accountId,
                        email = accountEmail,
                        nickname = accountNickname,
                        password = password
                    )
                )
            ).thenThrow(
                HttpException(
                    Response.error<String>(
                        400,
                        """
                            {
                              "className": "MethodArgumentNotValidException",
                              "errorMessage": "[이메일 형식에 맞지 않습니다]",
                              "code": 4,
                              "errorTrace": "org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:139)"
                            }
                    """.trimIndent().toResponseBody()
                    )
                )
            )

            Assert.assertEquals(
                SignUpResult.Failed("[이메일 형식에 맞지 않습니다]"),
                signUpRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }
    }

    @Test
    fun `API의 signUp에서 이미 존재하는 이메일 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() {
        TestCoroutineScope().launch {
            val accountId = "aaa"
            val accountEmail = "asdf"
            val accountNickname = "asdf"
            val password = "asdf"

            Mockito.`when`(
                noAuthApi.signUp(
                    SignUpRequest(
                        account = accountId,
                        email = accountEmail,
                        nickname = accountNickname,
                        password = password
                    )
                )
            ).thenThrow(
                HttpException(
                    Response.error<String>(
                        400,
                        """
                            {
                              "className": "NonCriticalException",
                              "errorMessage": "이미 존재하는 이메일입니다",
                              "code": 125,
                              "errorTrace": "in.koala.serviceImpl.UserServiceImpl.signUp(UserServiceImpl.java:167)"
                            }
                    """.trimIndent().toResponseBody()
                    )
                )
            )

            Assert.assertEquals(
                SignUpResult.Failed("이미 존재하는 이메일입니다"),
                signUpRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }
    }

    @Test
    fun `API의 signUp에서 이미 존재하는 아이디 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() {
        TestCoroutineScope().launch {
            val accountId = "aaa"
            val accountEmail = "asdf@asdf.com"
            val accountNickname = "asdf"
            val password = "asdf"

            Mockito.`when`(
                noAuthApi.signUp(
                    SignUpRequest(
                        account = accountId,
                        email = accountEmail,
                        nickname = accountNickname,
                        password = password
                    )
                )
            ).thenThrow(
                HttpException(
                    Response.error<String>(
                        400,
                        """
                            {
                              "className": "NonCriticalException",
                              "errorMessage": "이미 존재하는 아이디입니다",
                              "code": 123,
                              "errorTrace": "in.koala.serviceImpl.UserServiceImpl.signUp(UserServiceImpl.java:162)"
                            }
                    """.trimIndent().toResponseBody()
                    )
                )
            )

            Assert.assertEquals(
                SignUpResult.Failed("이미 존재하는 아이디입니다"),
                signUpRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }
    }

    @Test
    fun `API의 signUp에서 이미 존재하는 닉네임 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() {
        TestCoroutineScope().launch {
            val accountId = "aaa"
            val accountEmail = "asdf@asdf.com"
            val accountNickname = "asdf"
            val password = "asdf"

            Mockito.`when`(
                noAuthApi.signUp(
                    SignUpRequest(
                        account = accountId,
                        email = accountEmail,
                        nickname = accountNickname,
                        password = password
                    )
                )
            ).thenThrow(
                HttpException(
                    Response.error<String>(
                        400,
                        """
                            {
                              "className": "NonCriticalException",
                              "errorMessage": "이미 존재하는 닉네임입니다",
                              "code": 124,
                              "errorTrace": "in.koala.serviceImpl.UserServiceImpl.signUp(UserServiceImpl.java:165)"
                            }
                    """.trimIndent().toResponseBody()
                    )
                )
            )

            Assert.assertEquals(
                SignUpResult.Failed("이미 존재하는 아이디입니다"),
                signUpRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }
    }

    @Test
    fun `API의 signUp에서 HttpException이 아닌 예외가 발생할 경우 signUp이 message가 포함된 SignUpResult_Error를 반환`() {
        TestCoroutineScope().launch {
            val accountId = "aaa"
            val accountEmail = "asdf@asdf.com"
            val accountNickname = "asdf"
            val password = "asdf"

            Mockito.`when`(
                noAuthApi.signUp(
                    SignUpRequest(
                        account = accountId,
                        email = accountEmail,
                        nickname = accountNickname,
                        password = password
                    )
                )
            ).thenThrow(
                Exception("Test exception")
            )

            Assert.assertEquals(
                SignUpResult.Failed("Test exception"),
                signUpRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }
    }

    @Test
    fun `API의 signUp에서 정상적으로 회원가입이 진행될 경우 signUp이 signup 입력 정보가 포함된 SignUpResult_OK를 반환`() {
        TestCoroutineScope().launch {
            val accountId = "string22"
            val accountEmail = "string@a.cd"
            val accountNickname = "string213"
            val password = "asdf"

            Mockito.`when`(
                noAuthApi.signUp(
                    SignUpRequest(
                        account = accountId,
                        email = accountEmail,
                        nickname = accountNickname,
                        password = password
                    )
                )
            ).thenReturn(
                Response.success(
                    Gson().fromJson(
                        """
                            {
                              "id": 71,
                              "account": "string22",
                              "find_email": "string@a.cd",
                              "nickname": "string213",
                              "user_type": 0,
                              "is_auth": 0,
                              "created_at": "2022-01-01T13:40:04.000+00:00",
                              "updated_at": "2022-01-01T13:40:04.000+00:00"
                            }
                        """.trimIndent(),
                        SignUpResultResponse::class.java
                    )
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
                signUpRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }
    }
}