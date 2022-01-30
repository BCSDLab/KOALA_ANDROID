package im.koala.data.source.remote

import com.google.gson.Gson
import com.google.gson.JsonParseException
import im.koala.data.api.AuthApi
import im.koala.data.api.NoAuthApi
import im.koala.data.api.response.ResponseWrapper
import im.koala.data.api.response.signup.SignUpResultResponse
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.data.repository.remote.UserRemoteDataSourceImpl
import im.koala.domain.entity.signup.SignUpResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
class SignUpRemoteDataSourceTest {
    @Mock
    private lateinit var noAuthApi: NoAuthApi

    @Mock
    private lateinit var authApi: AuthApi

    @InjectMocks
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    @Before
    fun init() {
        noAuthApi = mock()
        userRemoteDataSource = UserRemoteDataSourceImpl(noAuthApi, authApi)
    }

    @Test
    fun `API의 checkAccount에서 존재하지 않는 Account를 반환할 경우 checkIdIsAvailable이 true를 반환`() =
        runBlockingTest {
            val id = "abc"

            whenever(noAuthApi.checkAccount(any()))
                .thenThrow(
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

            Assert.assertTrue(userRemoteDataSource.checkIdIsAvailable(id))
        }

    @Test
    fun `API의 checkAccount에서 존재하는 Account를 반환할 경우 checkIdIsAvailable이 false를 반환`() =
        runBlockingTest {
            val id = "abc"

            whenever(noAuthApi.checkAccount(any()))
                .thenReturn(
                    ResponseWrapper(
                        body = KOALA_API_SIGN_UP_CHECK_ID_OK_MESSAGE,
                        code = 200
                    )
                )

            Assert.assertFalse(userRemoteDataSource.checkIdIsAvailable(id))
        }

    @Test
    fun `API의 checkAccount에서 예외가 발생할 경우 checkIdIsAvailable이 그대로 예외 전달`() = runBlockingTest {
        val id = "abc"

        whenever(noAuthApi.checkAccount(any()))
            .thenThrow(
                JsonParseException("")
            )

        Assert.assertThrows(JsonParseException::class.java) {
            runBlockingTest {
                userRemoteDataSource.checkIdIsAvailable(id)
            }
        }
    }

    @Test
    fun `API의 checkEmail에서 존재하지 않는 Email을 반환할 경우 checkEmailIsAvailable이 true를 반환`() =
        runBlockingTest {
            val email = "abc@abc.com"

            whenever(noAuthApi.checkEmail(any()))
                .thenReturn(
                    ResponseWrapper(
                        body = KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE,
                        code = 200
                    )
                )

            Assert.assertTrue(userRemoteDataSource.checkEmailIsAvailable(email))
        }

    @Test
    fun `API의 checkEmail에서 존재하는 Email을 반환할 경우 checkEmailIsAvailable이 false를 반환`() =
        runBlockingTest {
            val email = "abc@abc.com"

            whenever(noAuthApi.checkEmail(any()))
                .thenThrow(
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

            Assert.assertFalse(userRemoteDataSource.checkEmailIsAvailable(email))
        }

    @Test
    fun `API의 checkEmail에서 예외가 발생할 경우 checkEmailIsAvailable이 그대로 예외 전달`() = runBlockingTest {
        val id = "abc"

        whenever(noAuthApi.checkEmail(any()))
            .thenThrow(
                JsonParseException("")
            )

        Assert.assertThrows(JsonParseException::class.java) {
            runBlockingTest {
                userRemoteDataSource.checkEmailIsAvailable(id)
            }
        }
    }

    @Test
    fun `API의 checkNickname에서 존재하지 않는 Nickname을 반환할 경우 checkNicknameIsAvailable이 true를 반환`() =
        runBlockingTest {
            val nickname = "abc"

            whenever(noAuthApi.checkNickname(any()))
                .thenReturn(
                    ResponseWrapper(
                        body = KOALA_API_SIGN_UP_CHECK_NICKNAME_OK_MESSAGE,
                        code = 200
                    )
                )

            Assert.assertTrue(userRemoteDataSource.checkNicknameIsAvailable(nickname))
        }

    @Test
    fun `API의 checkNickname에서 존재하는 Nickname을 반환할 경우 checkNicknameIsAvailable이 false를 반환`() =
        runBlockingTest {
            val nickname = "abc"

            whenever(noAuthApi.checkNickname(any()))
                .thenThrow(
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

            Assert.assertFalse(userRemoteDataSource.checkNicknameIsAvailable(nickname))
        }

    @Test
    fun `API의 checkNickname에서 예외가 발생할 경우 checkNicknameIsAvailable이 그대로 예외 전달`() = runBlockingTest {
        val id = "abc"

        whenever(noAuthApi.checkNickname(any()))
            .thenThrow(
                JsonParseException("")
            )

        Assert.assertThrows(JsonParseException::class.java) {
            runBlockingTest {
                userRemoteDataSource.checkNicknameIsAvailable(id)
            }
        }
    }

    @Test
    fun `API의 signUp에서 이메일 형식에 맞지 않는 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() =
        runBlockingTest {
            val accountId = "aaa"
            val accountEmail = "asdf"
            val accountNickname = "asdf"
            val password = "asdf"

            whenever(noAuthApi.signUp(any())).thenThrow(
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
                    """
                            .trimIndent()
                            .toResponseBody()
                    )
                )
            )

            assertThat(
                userRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password),
                equalTo(SignUpResult.Failed("[이메일 형식에 맞지 않습니다]"))
            )
        }

    @Test
    fun `API의 signUp에서 이미 존재하는 이메일 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() =
        runBlockingTest {
            val accountId = "aaa"
            val accountEmail = "asdf"
            val accountNickname = "asdf"
            val password = "asdf"

            whenever(noAuthApi.signUp(any())).thenThrow(
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
                userRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }

    @Test
    fun `API의 signUp에서 이미 존재하는 아이디 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() =
        runBlockingTest {
            val accountId = "aaa"
            val accountEmail = "asdf@asdf.com"
            val accountNickname = "asdf"
            val password = "asdf"

            whenever(noAuthApi.signUp(any())).thenThrow(
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
                userRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }

    @Test
    fun `API의 signUp에서 이미 존재하는 닉네임 예외가 발생할 경우 signUp이 errorMessage가 포함된 SignUpResult_Error를 반환`() =
        runBlockingTest {
            val accountId = "aaa"
            val accountEmail = "asdf@asdf.com"
            val accountNickname = "asdf"
            val password = "asdf"

            whenever(noAuthApi.signUp(any())).thenThrow(
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
                SignUpResult.Failed("이미 존재하는 닉네임입니다"),
                userRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }

    @Test
    fun `API의 signUp에서 정상적으로 회원가입이 진행될 경우 signUp이 signup 입력 정보가 포함된 SignUpResult_OK를 반환`() =
        runBlockingTest {
            val accountId = "string22"
            val accountEmail = "string@a.cd"
            val accountNickname = "string213"
            val password = "asdf"

            whenever(noAuthApi.signUp(any())).thenReturn(
                ResponseWrapper(
                    body = Gson().fromJson(
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
                    ),
                    code = 200
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
                userRemoteDataSource.signUp(accountId, accountEmail, accountNickname, password)
            )
        }
}