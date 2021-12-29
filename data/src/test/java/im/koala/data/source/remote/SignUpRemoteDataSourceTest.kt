package im.koala.data.source.remote

import im.koala.data.api.NoAuthApi
import im.koala.data.constant.KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class SignUpRemoteDataSourceTest {

    @Mock
    private lateinit var noAuthApi: NoAuthApi

    @Before
    fun setUpDataSource() {
        TestCoroutineScope().launch {
            val availableEmail = "aaa@bbb.ccc"
            val notAvailableEmail = "aaa@ccc.bbb"

            Mockito.`when`(noAuthApi.checkEmail(availableEmail)).thenReturn(
                KOALA_API_SIGN_UP_CHECK_EMAIL_OK_MESSAGE
            )
        }
    }

    @Test
    fun test() {
        TestCoroutineScope().launch {
        }
    }
}