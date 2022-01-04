package im.koala.bcsd.viewmodel

import im.koala.bcsd.di.ViewModelTestBuilder
import im.koala.bcsd.scenario.Scenario
import im.koala.bcsd.state.NetworkState
import im.koala.bcsd.viewmodel.base.ViewModelTest
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.KeywordResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
internal class MainViewModelTest : ViewModelTest() {
    private val viewModel = viewModelTestBuilder.provideMainViewModel()

    @Test
    fun `네트워크 통신 성공일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        ViewModelTestBuilder.remoteUserDataSource.chageScenario(Scenario.SUCCESS)
        val testObservable = viewModel.keywordState.test()
        viewModel.executeGetKeywordList()

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Success(mutableListOf<KeywordResponse>())
            )
        )
    }
    @Test
    fun `네트워크 통신 실패일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        ViewModelTestBuilder.remoteUserDataSource.chageScenario(Scenario.FAIL)
        val testObservable = viewModel.keywordState.test()
        viewModel.executeGetKeywordList()

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.FAIL)
            )
        )
    }

    @Test
    fun `네트워크 통신 에러일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        ViewModelTestBuilder.remoteUserDataSource.chageScenario(Scenario.ERROR)
        val testObservable = viewModel.keywordState.test()
        viewModel.executeGetKeywordList()

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.UNKOWN)
            )
        )
    }
}