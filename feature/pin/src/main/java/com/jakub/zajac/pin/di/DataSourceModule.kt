package com.jakub.zajac.pin.di

import com.jakub.zajac.pin.data.data_source.PinLocalDataSource
import com.jakub.zajac.storage.dao.PinDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun providePinLocalDataSource(
        pinDao: PinDao
    ): PinLocalDataSource = PinLocalDataSource(pinDao)
}