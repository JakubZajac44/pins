package com.jakub.zajac.storage.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jakub.zajac.storage.dao.PinDao
import com.jakub.zajac.storage.model.PinEntity

@Database(
    entities = [PinEntity::class], version = 1
)
abstract class PinDataBase : RoomDatabase() {
    abstract fun pinDao(): PinDao

    companion object {

        private const val DATABASE_NAME = "pin_db"

        fun getDatabase(context: Context): PinDataBase {
            return Room.databaseBuilder(
                context.applicationContext, PinDataBase::class.java, DATABASE_NAME
            ).build()
        }
    }
}