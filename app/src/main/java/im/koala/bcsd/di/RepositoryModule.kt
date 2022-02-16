package im.koala.bcsd.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import im.koala.data.api.AuthApi
import im.koala.data.api.GooglePostTokenApi
import im.koala.data.api.NoAuthApi
import im.koala.data.repository.GooglePostTokenRepositoryImpl
import im.koala.data.repository.KeywordAddRepositoryImpl
import im.koala.data.repository.KeywordRepositoryImpl
import im.koala.data.repository.UserRepositoryImpl
import im.koala.data.repository.local.*
import im.koala.data.repository.remote.*
import im.koala.domain.repository.GooglePostTokenRepository
import im.koala.domain.repository.KeywordAddRepository
import im.koala.domain.repository.KeywordRepository
import im.koala.domain.repository.UserRepository
import im.koala.domain.usecase.GetKeywordListUseCase
import im.koala.domain.usecase.GetDeviceTokenUseCase
import im.koala.domain.usecase.GooglePostAccessTokenUseCase
import im.koala.domain.usecase.SnsLoginUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        @NOAUTH noAuthApi: NoAuthApi,
        @AUTH authApi: AuthApi
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(noAuthApi, authApi)
    }

    @Singleton
    @Provides
    fun provideUserLocalDataSource(): UserLocalDataSource {
        return UserLocalDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        authRemoteDataSource: UserRemoteDataSource,
        noAuthLocalDataSource: UserLocalDataSource
    ): UserRepository {
        return UserRepositoryImpl(authRemoteDataSource, noAuthLocalDataSource)
    }

    @Singleton
    @Provides
    fun provideGooglePostAccessTokenRepository(
        @GOOGLE googlePostTokenApi: GooglePostTokenApi
    ): GooglePostTokenRepository {
        return GooglePostTokenRepositoryImpl(googlePostTokenApi)
    }

    @Provides
    @Singleton
    fun provideSnsUseCase(
        userRepository: UserRepository
    ): SnsLoginUseCase {
        return SnsLoginUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetKeywordListUseCase(
        userRepository: UserRepository
    ): GetKeywordListUseCase {
        return GetKeywordListUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetDeviceTokenUseCase(): GetDeviceTokenUseCase {
        return GetDeviceTokenUseCase()
    }

    @Provides
    @Singleton
    fun provideGooglePostAccessTokenUseCase(
        googlePostTokenRepository: GooglePostTokenRepository
    ): GooglePostAccessTokenUseCase {
        return GooglePostAccessTokenUseCase(googlePostTokenRepository)
    }

    @Provides
    @Singleton
    fun provideAlarmSiteDataSource(): AlarmSiteDataSource {
        return AlarmSiteDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideKeywordAddRemoteDataSource(
        @AUTH authApi: AuthApi
    ): KeywordAddRemoteDataSource {
        return KeywordAddRemoteDataSourceImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideKeywordAddLocalDataSource(): KeywordAddLocalDataSource {
        return KeywordAddLocalDataSourceImpl()
    }

    @Provides
    @Singleton
    fun provideKeywordAddRepository(
        keywordAddRemoteDataSource: KeywordAddRemoteDataSource,
        keywordAddLocalDataSource: KeywordAddLocalDataSource
    ): KeywordAddRepository {
        return KeywordAddRepositoryImpl(keywordAddRemoteDataSource, keywordAddLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideKeywordRemoteDataSource(
        @AUTH authApi: AuthApi
    ): KeywordRemoteDataSource {
        return KeywordRemoteDataSourceImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideKeywordLocalDataSource(
        @ApplicationContext context: Context
    ): KeywordLocalDataSource {
        return KeywordLocalDataSourceImpl(context)
    }

    @Provides
    @Singleton
    fun provideKeywordRepository(
        keywordLocalDataSource: KeywordLocalDataSource,
        keywordRemoteDataSource: KeywordRemoteDataSource
    ): KeywordRepository {
        return KeywordRepositoryImpl(
            keywordLocalDataSource, keywordRemoteDataSource
        )
    }
}