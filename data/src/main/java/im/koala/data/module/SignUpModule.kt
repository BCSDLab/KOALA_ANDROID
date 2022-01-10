package im.koala.data.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.data.repository.SignUpRepositoryImpl
import im.koala.data.source.SignUpDataSource
import im.koala.data.source.local.SignUpLocalDataSource
import im.koala.data.source.remote.SignUpRemoteDataSource
import im.koala.domain.repository.SignUpRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class SignUpModule {

    @Binds
    @RemoteDataSource
    abstract fun bindSignUpRemoteDataSource(signUpRemoteDataSource: SignUpRemoteDataSource): SignUpDataSource

    @Binds
    @LocalDataSource
    abstract fun bindSignUpLocalDataSource(signUpLocalDataSource: SignUpLocalDataSource): SignUpDataSource

    @Binds
    abstract fun bindSignUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository
}