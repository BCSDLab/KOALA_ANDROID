package im.koala.bcsd.di

import im.koala.bcsd.datasource.TestGooglePostToken
import im.koala.bcsd.datasource.TestUserLocalDataSource
import im.koala.bcsd.datasource.TestUserRemoteDataSource
import im.koala.bcsd.ui.login.LoginViewModel
import im.koala.data.repository.GooglePostTokenRepositoryImpl
import im.koala.data.repository.UserRepositoryImpl
import im.koala.domain.usecase.GooglePostAccessTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase

class ViewModelTestBuilder {
    /* Repository */
    fun provideUserRepository(): UserRepositoryImpl =
        UserRepositoryImpl(remoteUserDataSource, localUserDataSource)

    fun provideGooglePostTokenRepository(): GooglePostTokenRepositoryImpl =
        GooglePostTokenRepositoryImpl(googlePostToken)

    /* UseCase */
    fun provideSnsLoginUsecase(): SnsLoginUseCase = SnsLoginUseCase(provideUserRepository())

    fun provideGooglePostAccessTokenUsecase(): GooglePostAccessTokenUseCase =
        GooglePostAccessTokenUseCase(provideGooglePostTokenRepository())

    /* ViewModel */
    fun provideLoginViewModel(): LoginViewModel =
        LoginViewModel(provideSnsLoginUsecase(),provideGooglePostAccessTokenUsecase())

    /* Datasource - Datasource는 Scenario값을 변경해야하기에 변수 선언 및 싱글톤으로 설계*/
    companion object {
        val localUserDataSource: TestUserLocalDataSource = TestUserLocalDataSource()
        val remoteUserDataSource: TestUserRemoteDataSource = TestUserRemoteDataSource()
        val googlePostToken: TestGooglePostToken = TestGooglePostToken()
    }
}