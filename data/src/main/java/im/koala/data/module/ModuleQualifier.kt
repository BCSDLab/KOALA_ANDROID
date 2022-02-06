package im.koala.data.module

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource