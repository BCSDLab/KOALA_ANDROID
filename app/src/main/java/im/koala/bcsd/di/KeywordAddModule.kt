package im.koala.bcsd.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.data.api.AuthApi
import im.koala.data.repository.KeywordAddRepositoryImpl
import im.koala.data.repository.local.AlarmSiteDataSource
import im.koala.data.repository.local.AlarmSiteDataSourceImpl
import im.koala.data.repository.local.KeywordAddLocalDataSource
import im.koala.data.repository.local.KeywordAddLocalDataSourceImpl
import im.koala.data.repository.remote.KeywordAddRemoteDataSource
import im.koala.data.repository.remote.KeywordAddRemoteDataSourceImpl
import im.koala.domain.repository.KeywordAddRepository

@Module
@InstallIn(SingletonComponent::class)
object KeywordAddModule {

    @Provides
    fun provideAlarmSiteDataSource(): AlarmSiteDataSource {
        return AlarmSiteDataSourceImpl()
    }

    @Provides
    fun provideKeywordAddRemoteDataSource(
        @AUTH authApi: AuthApi
    ): KeywordAddRemoteDataSource {
        return KeywordAddRemoteDataSourceImpl(authApi)
    }

    @Provides
    fun provideKeywordAddLocalDataSource(): KeywordAddLocalDataSource {
        return KeywordAddLocalDataSourceImpl()
    }

    @Provides
    fun provideKeywordAddRepository(
        keywordAddRemoteDataSource: KeywordAddRemoteDataSource,
        keywordAddLocalDataSource: KeywordAddLocalDataSource
    ): KeywordAddRepository {
        return KeywordAddRepositoryImpl(keywordAddRemoteDataSource, keywordAddLocalDataSource)
    }
}