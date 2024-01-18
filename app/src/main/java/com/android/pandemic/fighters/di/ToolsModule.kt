package com.android.pandemic.fighters.di

import android.content.Context
import com.android.pandemic.fighters.utils.location.LocationEmitter
import com.android.pandemic.fighters.utils.location.LocationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToolsModule {

    @Provides
    @Singleton
    fun provideLocationEmitter() = LocationEmitter()

    @Provides
    @Singleton
    fun provideLocationProvider(@ApplicationContext context: Context, locationEmitter: LocationEmitter) = LocationProvider(context, locationEmitter)
}