package hu.bme.aut.moblab.memories.di.network

import android.util.Log
import androidx.databinding.ktx.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import hu.bme.aut.moblab.memories.di.NetworkModule
import hu.bme.aut.moblab.memories.network.MemoriesAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object NetworkMockModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer()

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor { message ->
                    if (BuildConfig.DEBUG) {
                        Log.d("HttpLoggingInterceptor", message)
                    }
                }.setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        mockWebServer: MockWebServer,
        converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun memoriesApi(retrofit: Retrofit): MemoriesAPI = retrofit.create()

}