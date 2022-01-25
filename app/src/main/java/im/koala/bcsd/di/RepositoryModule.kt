package im.koala.bcsd.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import im.koala.data.api.GooglePostTokenApi
import im.koala.data.api.NoAuthApi
import im.koala.data.repository.GooglePostTokenRepositoryImpl
import im.koala.data.repository.UserRepositoryImpl
import im.koala.data.repository.local.UserLocalDataSource
import im.koala.data.repository.local.UserLocalDataSourceImpl
import im.koala.data.repository.remote.UserRemoteDataSource
import im.koala.data.repository.remote.UserRemoteDataSourceImpl
import im.koala.domain.repository.GooglePostTokenRepository
import im.koala.domain.repository.UserRepository
import im.koala.domain.usecase.GetDeviceTokenUseCase
import im.koala.domain.usecase.GooglePostAccessTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideUserRemoteDataSource(
        @NOAUTH noAuthApi: NoAuthApi
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(noAuthApi)
    }

    @Provides
    fun provideUserLocalDataSource(): UserLocalDataSource {
        return UserLocalDataSourceImpl()
    }

    @Provides
    fun provideuserRepository(
        authRemoteDataSource: UserRemoteDataSource,
        noAuthLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(authRemoteDataSource, noAuthLocalDataSource)
    }

    @Provides
    @ViewModelScoped
    fun provideSnsUseCase(
        noAuthRepository: UserRepository
    ): SnsLoginUseCase {
        return SnsLoginUseCase(noAuthRepository)
    }

    @Provides
    fun providerGooglePostAccessTokenRepository(
        @GOOGLE googlePostTokenApi: GooglePostTokenApi
    ): GooglePostTokenRepository {
        return GooglePostTokenRepositoryImpl(googlePostTokenApi)
    }

    @Provides
    @ViewModelScoped
    fun provideGooglePostAccessTokenUseCase(
        googlePostTokenRepository: GooglePostTokenRepository
    ): GooglePostAccessTokenUseCase {
        return GooglePostAccessTokenUseCase(googlePostTokenRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDeviceTokenUseCase(): GetDeviceTokenUseCase {
        return GetDeviceTokenUseCase()
    }
}