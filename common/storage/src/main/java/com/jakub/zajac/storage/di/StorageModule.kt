package com.jakub.zajac.storage.di

import android.content.Context
import com.jakub.zajac.storage.dao.PinDao
import com.jakub.zajac.storage.db.PinDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun providePinDataBase(@ApplicationContext context: Context): PinDataBase =
        PinDataBase.getDatabase(context)

    @Provides
    @Singleton
    fun providePinDao(dataBase: PinDataBase): PinDao = dataBase.pinDao()
}