//package im.koala.bcsd.di
//
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.SupervisorJob
//import javax.inject.Qualifier
//import javax.inject.Singleton
//
//@Retention(AnnotationRetention.RUNTIME)
//@Qualifier
//annotation class DefaultDispatcher
//
//@Retention(AnnotationRetention.RUNTIME)
//@Qualifier
//annotation class IoDispatcher
//
//@Retention(AnnotationRetention.RUNTIME)
//@Qualifier
//annotation class MainDispatcher
//
//@Retention(AnnotationRetention.RUNTIME)
//@Qualifier
//annotation class ApplicationScope
//
//@Retention(AnnotationRetention.RUNTIME)
//@Qualifier
//annotation class IoScope
//
//@Module
//@InstallIn(SingletonComponent::class)
//object CoroutineModule {
//
//    @DefaultDispatcher
//    @Provides
//    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
//
//    @IoDispatcher
//    @Provides
//    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
//
//    @MainDispatcher
//    @Provides
//    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
//
//    @Singleton
//    @ApplicationScope
//    @Provides
//    fun providesCoroutineScope(
//        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
//    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
//}