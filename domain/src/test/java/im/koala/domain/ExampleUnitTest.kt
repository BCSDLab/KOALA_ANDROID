package im.koala.domain

import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun foo(){
        var a = 0
        flow<String> {
            a = 2
        }
        assertEquals(2,a)
    }
}