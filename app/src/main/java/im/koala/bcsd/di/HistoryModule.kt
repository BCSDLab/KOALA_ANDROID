package im.koala.bcsd.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.data.api.AuthApi
import im.koala.data.repository.HistoryRepositoryImpl
import im.koala.data.repository.remote.HistoryRemoteDataSource
import im.koala.data.repository.remote.HistoryRemoteDataSourceImpl
import im.koala.domain.repository.HistoryRepository

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Provides
    fun provideHistoryRemoteDataSource(
        @AUTH authApi: AuthApi
    ): HistoryRemoteDataSource {
        return HistoryRemoteDataSourceImpl(authApi)
    }

    @Provides
    fun provideHistoryRepository(
        historyRemoteDataSource: HistoryRemoteDataSource
    ): HistoryRepository {
        return HistoryRepositoryImpl(historyRemoteDataSource)
    }
}