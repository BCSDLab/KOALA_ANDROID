package im.koala.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import im.koala.data.BuildConfig
import im.koala.data.api.NoAuthApi
import im.koala.data.constant.KOALA_API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    val isDebug get() = BuildConfig.BUILD_TYPE == "debug"

    private val baseHttpLoggingInterceptor: HttpLoggingInterceptor
        get() = HttpLoggingInterceptor().apply {
            level = if(isDebug) {
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

    private val noAuthRetrofit : Retrofit
    get() = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(KOALA_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideNoAuthApi() : NoAuthApi {
        return noAuthRetrofit.create(NoAuthApi::class.java)
    }
}