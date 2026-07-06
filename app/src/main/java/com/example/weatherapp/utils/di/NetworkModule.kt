package com.example.weatherapp.utils.di

import com.example.weatherapp.data.network.ApiServices
import com.example.weatherapp.utils.API_KEY
import com.example.weatherapp.utils.APPID
import com.example.weatherapp.utils.BASE_URL
import com.example.weatherapp.utils.CONNECTION_TIME
import com.example.weatherapp.utils.NAMED_PING
import com.example.weatherapp.utils.PING_INTERVAL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.weatherapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideClient(
        timeout: Long, @Named(NAMED_PING) ping: Long, interceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val url = chain.request().url.newBuilder().addQueryParameter(APPID, API_KEY).build()
            val request = chain.request().newBuilder().url(url).build()
            chain.proceed(request)
        }.also { client ->
            client.addInterceptor(interceptor)
        }
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .pingInterval(ping, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideTimeout(): Long = CONNECTION_TIME

    @Provides
    @Singleton
    @Named(NAMED_PING)
    fun providePingInterval(): Long = PING_INTERVAL
}