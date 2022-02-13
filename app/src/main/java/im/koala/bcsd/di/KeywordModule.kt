package im.koala.bcsd.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import im.koala.data.api.AuthApi
import im.koala.data.repository.KeywordRepositoryImpl
import im.koala.data.repository.local.KeywordLocalDataSource
import im.koala.data.repository.local.KeywordLocalDataSourceImpl
import im.koala.data.repository.remote.KeywordRemoteDataSource
import im.koala.data.repository.remote.KeywordRemoteDataSourceImpl
import im.koala.domain.repository.KeywordRepository

@Module
@InstallIn(SingletonComponent::class)
object KeywordModule {

    @Provides
    fun provideKeywordRemoteDataSource(
        @AUTH authApi: AuthApi
    ): KeywordRemoteDataSource {
        return KeywordRemoteDataSourceImpl(authApi)
    }

    @Provides
    fun provideKeywordLocalDataSource(
        @ApplicationContext context: Context
    ): KeywordLocalDataSource {
        return KeywordLocalDataSourceImpl(context)
    }

    @Provides
    fun provideKeywordRepository(
        keywordLocalDataSource: KeywordLocalDataSource,
        keywordRemoteDataSource: KeywordRemoteDataSource
    ): KeywordRepository {
        return KeywordRepositoryImpl(
            keywordLocalDataSource, keywordRemoteDataSource
        )
    }
}