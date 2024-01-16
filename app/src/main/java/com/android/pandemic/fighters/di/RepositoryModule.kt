package com.android.pandemic.fighters.di

import com.android.pandemic.fighters.api.PandemicService
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
    fun provideVirusRepository(pandemicService: PandemicService) = VirusRepository(pandemicService)
}