package com.android.pandemic.fighters.di

import com.android.pandemic.fighters.api.GeolocationService
import com.android.pandemic.fighters.api.PandemicService
import com.android.pandemic.fighters.db.AppDatabase
import com.android.pandemic.fighters.repositories.GeolocationRepository
import com.android.pandemic.fighters.repositories.VirusRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideVirusRepository(pandemicService: PandemicService, appDatabase: AppDatabase) = VirusRepository(pandemicService, appDatabase.reportedCasesDao())

    @Provides
    @Singleton
    fun provideGeolocationRepository(geolocationService: GeolocationService) =
        GeolocationRepository(geolocationService)
}