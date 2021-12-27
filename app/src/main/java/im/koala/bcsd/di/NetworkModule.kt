package im.koala.bcsd.di

import com.orhanobut.hawk.Hawk
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.bcsd.KoalaApp
import im.koala.data.api.AuthApi
import im.koala.data.api.NoAuthApi
import im.koala.data.constants.ACCESS_TOKEN
import im.koala.data.constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AUTH

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NOAUTH

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val httpLogginInterceptor = HttpLoggingInterceptor().apply {
        level = if (KoalaApp.instance.isApplicationDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.HEADERS
        }
    }
    /*
    @Target(
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.FUNCTION,
        AnnotationTarget.EXPRESSION
    )

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class NO_AUTH

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AUTH

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class REFRESH_AUTH


     */

    @AUTH
    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val accessToken = Hawk.get(ACCESS_TOKEN, "")
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(newRequest)
        }

    }
    @NOAUTH
    @Provides
    @Singleton
    fun provideNoAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLogginInterceptor)
        }.build()
    }

    @AUTH
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(@AUTH authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLogginInterceptor)
            addInterceptor(authInterceptor)
        }.build()
    }
    @NOAUTH
    @Provides
    @Singleton
    fun provideNoAuthRetrofit(@NOAUTH okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @AUTH
    @Provides
    @Singleton
    fun provideAuthRetrofit(@AUTH okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @NOAUTH
    @Provides
    @Singleton
    fun provideNoAuthApi(@NOAUTH retrofit: Retrofit): NoAuthApi {
        return retrofit.create(NoAuthApi::class.java)
    }

    @AUTH
    @Provides
    @Singleton
    fun provideAuthApi(@AUTH retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

}