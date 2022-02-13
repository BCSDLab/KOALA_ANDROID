package im.koala.data.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.data.BuildConfig
import im.koala.data.api.AuthApi
import im.koala.data.api.NoAuthApi
import im.koala.data.constant.KOALA_API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    private val isDebug get() = BuildConfig.BUILD_TYPE == "debug"

    private val gson: Gson
        get() = GsonBuilder().setLenient().create()

    private val baseHttpLoggingInterceptor: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = if (isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.HEADERS
            }
        }

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(baseHttpLoggingInterceptor)
            .build()

    private val noAuthRetrofit: Retrofit
        get() = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(KOALA_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    private val authRetrofit: Retrofit
        get() = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(KOALA_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    fun provideNoAuthApi(): NoAuthApi {
        return noAuthRetrofit.create(NoAuthApi::class.java)
    }

    @Provides
    fun provideAuthApi(): AuthApi {
        return authRetrofit.create(AuthApi::class.java)
    }
}