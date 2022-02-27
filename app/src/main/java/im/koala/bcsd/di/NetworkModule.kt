package im.koala.bcsd.di

import com.orhanobut.hawk.Hawk
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.bcsd.KoalaApp
import im.koala.bcsd.util.TokenAuthenticator
import im.koala.data.api.AuthApi
import im.koala.data.api.GooglePostTokenApi
import im.koala.data.api.NoAuthApi
import im.koala.data.constants.ACCESS_TOKEN
import im.koala.data.constants.BASE_URL
import im.koala.data.constants.GOOGLE_OAUTH
import im.koala.data.constants.REFRESH_TOKEN
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

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class REFRESH

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GOOGLE

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

    @AUTH
    @Provides
    @Singleton
    fun provideTokenAuthenticator(@REFRESH refreshRetrofit: Retrofit): TokenAuthenticator {
        return TokenAuthenticator(
            KoalaApp.instance.applicationContext,
            refreshRetrofit
        )
    }

    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val refreshToken = Hawk.get(REFRESH_TOKEN, "")
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("RefreshToken", "Bearer $refreshToken")
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
    fun provideAuthOkHttpClient(
        @AUTH authInterceptor: Interceptor,
        @AUTH tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLogginInterceptor)
            addInterceptor(authInterceptor)
            authenticator(tokenAuthenticator)
        }.build()
    }

    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshOkHttpClient(
        @REFRESH refreshAuthInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLogginInterceptor)
            addInterceptor(refreshAuthInterceptor)
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

    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshRetrofit(@REFRESH okHttpClient: OkHttpClient): Retrofit {
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

    @REFRESH
    @Provides
    @Singleton
    fun provideRefreshApi(@REFRESH retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @GOOGLE
    @Provides
    @Singleton
    fun provideGoogleOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
            addInterceptor(httpLogginInterceptor)
        }.build()
    }

    @GOOGLE
    @Provides
    @Singleton
    fun provideGoogleRetrofit(@GOOGLE okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GOOGLE_OAUTH)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GOOGLE
    @Provides
    @Singleton
    fun provideGooglePostTokenApi(@GOOGLE retrofit: Retrofit): GooglePostTokenApi {
        return retrofit.create(GooglePostTokenApi::class.java)
    }
}