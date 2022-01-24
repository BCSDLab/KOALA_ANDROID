package im.koala.bcsd.viewmodel

/*
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
internal class LoginViewModelTest : ViewModelTest() {

    private val viewModel = viewModelTestBuilder.provideLoginViewModel()

    @Before
    fun init() {
        viewModel.deviceToken = "token"
    }
    @Test
    fun `네트워크 통신 성공일 경우 올바른 값이 라이브데이터에 할당되는지 테스트`() {
        remoteUserDataSource.chageScenario(Scenario.SUCCESS)
        val testObservable = viewModel.snsLoginState.test()
        viewModel.executeKakaoLogin("token")

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
        viewModel.executeKakaoLogin("token")

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
        viewModel.executeKakaoLogin("token")

        testObservable.assertValueSequence(
            listOf(
                NetworkState.Uninitialized,
                NetworkState.Loading,
                NetworkState.Fail(CommonResponse.UNKOWN)
            )
        )
    }
}

 */