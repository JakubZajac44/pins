package com.jakub.zajac.pin.di

import com.jakub.zajac.pin.data.repository.PinRepositoryImpl
import com.jakub.zajac.pin.domain.repository.PinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providePinRepository(
        pinRepositoryImpl: PinRepositoryImpl
    ): PinRepository
}