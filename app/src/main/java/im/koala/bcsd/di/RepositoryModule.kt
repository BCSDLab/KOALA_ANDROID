package im.koala.bcsd.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import im.koala.data.api.AuthApi
import im.koala.data.api.GooglePostTokenApi
import im.koala.data.api.NoAuthApi
import im.koala.data.repository.GooglePostTokenRepositoryImpl
import im.koala.data.repository.KeywordAddRepositoryImpl
import im.koala.data.repository.UserRepositoryImpl
import im.koala.data.repository.local.AlarmSiteDataSource
import im.koala.data.repository.local.AlarmSiteDataSourceImpl
import im.koala.data.repository.local.UserLocalDataSource
import im.koala.data.repository.local.UserLocalDataSourceImpl
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.data.repository.remote.UserRemoteDataSourceImpl
import im.koala.domain.repository.GooglePostTokenRepository
import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.repository.UserRepository
import im.koala.domain.usecase.GetKeywordListUseCase
import im.koala.domain.usecase.GetDeviceTokenUseCase
import im.koala.domain.usecase.GooglePostAccessTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRemoteDataSource(
        @NOAUTH noAuthApi: NoAuthApi,
        @AUTH authApi: AuthApi
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(noAuthApi, authApi)
    }

    @Provides
    fun provideUserLocalDataSource(): UserLocalDataSource {
        return UserLocalDataSourceImpl()
    }

    @Provides
    fun provideAlarmSiteDataSource(): AlarmSiteDataSource {
        return AlarmSiteDataSourceImpl()
    }

    @Provides
    fun provideuserRepository(
        authRemoteDataSource: UserRemoteDataSource,
        noAuthLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(authRemoteDataSource, noAuthLocalDataSource)
    }

    @Provides
    fun provideKeywordAddRepository(
        authRemoteDataSource: UserRemoteDataSource,
        authLocalDataSource: UserLocalDataSource
    ): KeywordAddRepository {
        return KeywordAddRepositoryImpl(authRemoteDataSource, authLocalDataSource)
    }

    @Provides
    fun provideGooglePostAccessTokenRepository(
        @GOOGLE googlePostTokenApi: GooglePostTokenApi
    ): GooglePostTokenRepository {
        return GooglePostTokenRepositoryImpl(googlePostTokenApi)
    }

    @Provides
    @ViewModelScoped
    fun provideSnsUseCase(
        userRepository: UserRepository
    ): SnsLoginUseCase {
        return SnsLoginUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetKeywordListUseCase(
        userRepository: UserRepository
    ): GetKeywordListUseCase {
        return GetKeywordListUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDeviceTokenUseCase(): GetDeviceTokenUseCase {
        return GetDeviceTokenUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideGooglePostAccessTokenUseCase(
        googlePostTokenRepository: GooglePostTokenRepository
    ): GooglePostAccessTokenUseCase {
        return GooglePostAccessTokenUseCase(googlePostTokenRepository)
    }
}