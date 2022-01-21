package im.koala.data.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.data.repository.KeywordRepositoryImpl
import im.koala.data.source.KeywordDataSource
import im.koala.data.source.local.KeywordLocalDataSource
import im.koala.data.source.remote.KeywordRemoteDataSource
import im.koala.domain.repository.KeywordRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class KeywordModule {

    @Binds
    @RemoteDataSource
    abstract fun bindKeywordRemoteDataSource(keywordRemoteDataSource: KeywordRemoteDataSource): KeywordDataSource

    @Binds
    @LocalDataSource
    abstract fun bindKeywordLocalDataSource(keywordLocalDataSource: KeywordLocalDataSource): KeywordDataSource

    @Binds
    abstract fun bindKeywordRepository(keywordRepositoryImpl: KeywordRepositoryImpl): KeywordRepository
}