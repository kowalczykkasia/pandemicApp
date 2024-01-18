package com.android.pandemic.fighters.di

import com.android.pandemic.fighters.BuildConfig
import com.android.pandemic.fighters.api.GeolocationService
import com.android.pandemic.fighters.api.PandemicService
import com.android.pandemic.fighters.utils.GEOLOCATION_SERVICE
import com.google.gson.Gson
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

@Module
@InstallIn(SingletonComponent::class)
object RemoteSourceModule {

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ) = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    @Provides
    @Singleton
    @Named(GEOLOCATION_SERVICE)
    fun provideRetrofitForGeolocation(
        okHttpClient: OkHttpClient,
        gson: Gson
    ) = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .baseUrl(BuildConfig.GEOLOCATION_BASE_URL)
        .build()

    @Provides
    @Singleton
    fun providePandemicService(retrofit: Retrofit) = retrofit.create(PandemicService::class.java)

    @Provides
    @Singleton
    fun provideGeolocationService(@Named(GEOLOCATION_SERVICE) retrofit: Retrofit) = retrofit.create(GeolocationService::class.java)
}