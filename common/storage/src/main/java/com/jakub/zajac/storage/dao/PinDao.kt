package com.jakub.zajac.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jakub.zajac.storage.model.PinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PinDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPin(pinEntity: PinEntity)

    @Delete
    suspend fun deletePin(pinEntity: PinEntity)

    @Update
    suspend fun updatePin(pinEntity: PinEntity)

    @Query("Select * from PinEntity")
    fun getAllPin(): Flow<List<PinEntity>>
}