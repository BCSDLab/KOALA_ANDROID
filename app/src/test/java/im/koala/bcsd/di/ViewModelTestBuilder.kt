package im.koala.bcsd.di

import im.koala.bcsd.datasource.TestUserLocalDataSource
import im.koala.bcsd.datasource.TestUserRemoteDataSource
import im.koala.bcsd.ui.login.LoginViewModel
import im.koala.data.repository.UserRepositoryImpl
import im.koala.domain.usecase.GetDeviceTokenUseCase
import im.koala.domain.usecase.KakaoLoginUseCase

class ViewModelTestBuilder {
    /* Repository */
    fun provideUserRepository(): UserRepositoryImpl =
        UserRepositoryImpl(remoteUserDataSource, localUserDataSource)

    /* UseCase */
    fun provideKakaoLoginUsecase(): KakaoLoginUseCase = KakaoLoginUseCase(provideUserRepository())
    fun provideGetDeviceTokenUseCase(): GetDeviceTokenUseCase = GetDeviceTokenUseCase()

    /* ViewModel */
    fun provideLoginViewModel(): LoginViewModel = LoginViewModel(provideKakaoLoginUsecase(), provideGetDeviceTokenUseCase())

    /* Datasource - Datasource는 Scenario값을 변경해야하기에 변수 선언 및 싱글톤으로 설계*/
    companion object {
        val localUserDataSource: TestUserLocalDataSource = TestUserLocalDataSource()
        val remoteUserDataSource: TestUserRemoteDataSource = TestUserRemoteDataSource()
    }
}