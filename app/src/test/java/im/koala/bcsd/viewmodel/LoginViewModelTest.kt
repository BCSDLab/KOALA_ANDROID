package im.koala.bcsd.viewmodel

import im.koala.bcsd.di.ViewModelTestBuilder.Companion.remoteUserDataSource
import im.koala.bcsd.scenario.Scenario
import im.koala.bcsd.state.NetworkState
import im.koala.bcsd.viewmodel.base.ViewModelTest
import im.koala.domain.constants.GOOGLE
import im.koala.domain.constants.KAKAO
import im.koala.domain.constants.NAVER
import im.koala.domain.model.CommonResponse
import im.koala.domain.model.TokenResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
internal class LoginViewModelTest : ViewModelTest() {

    private val viewModel = viewModelTestBuilder.provideLoginViewModel()

    @Before
    fun init() {
    }

    @Test
    fun `네트워크 통신 성공일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.SUCCESS)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(KAKAO, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Success(TokenResponse.SUCCESS)
            )
        )
    }

    @Test
    fun `네트워크 통신 실패일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.FAIL)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(KAKAO, "token")

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
        remoteUserDataSource.chageScenario(Scenario.ERROR)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(KAKAO, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.UNKOWN)
            )
        )
    }

    @Test
    fun `네이버 네트워크 통신 성공일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.SUCCESS)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(NAVER, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Success(TokenResponse.SUCCESS)
            )
        )
    }

    @Test
    fun `네이버 네트워크 통신 실패일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.FAIL)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(KAKAO, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.FAIL)
            )
        )
    }

    @Test
    fun `네이버 네트워크 통신 에러일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.ERROR)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(NAVER, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.UNKOWN)
            )
        )
    }

    @Test
    fun `구글 네트워크 통신 성공일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.SUCCESS)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(GOOGLE, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Success(TokenResponse.SUCCESS)
            )
        )
    }

    @Test
    fun `구글 네트워크 통신 실패일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.FAIL)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(GOOGLE, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.FAIL)
            )
        )
    }

    @Test
    fun `구글 네트워크 통신 에러일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.ERROR)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeSnsLogin(GOOGLE, "token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.UNKOWN)
            )
        )
    }
}