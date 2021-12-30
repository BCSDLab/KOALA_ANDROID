package im.koala.bcsd.viewmodel.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import dagger.hilt.android.testing.HiltAndroidTest
import im.koala.bcsd.di.ViewModelTestBuilder
import im.koala.bcsd.livedata.LiveDataTestObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
internal abstract class ViewModelTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val viewModelTestBuilder: ViewModelTestBuilder = ViewModelTestBuilder()
    private val dispatcher = TestCoroutineDispatcher()
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // MainDispatcher를 초기화 해주어야 메모리 누수가 발생하지 않음
    }

    protected fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {
        val testObserver = LiveDataTestObserver<T>()
        observeForever(testObserver)

        return testObserver
    }
}