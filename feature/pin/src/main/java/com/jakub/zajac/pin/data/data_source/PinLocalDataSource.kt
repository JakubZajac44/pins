package com.jakub.zajac.pin.data.data_source

import com.jakub.zajac.storage.dao.PinDao
import com.jakub.zajac.storage.model.PinEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PinLocalDataSource @Inject constructor(
    private val pinDao: PinDao
) {

    suspend fun insertPin(pinEntity: PinEntity) {
        pinDao.insertPin(pinEntity)
    }

    suspend fun updatePint(pinEntity: PinEntity) {
        pinDao.updatePin(pinEntity)
    }

    suspend fun deletePin(pinEntity: PinEntity) {
        pinDao.deletePin(pinEntity)
    }

    fun getAllPin(): Flow<List<PinEntity>> {
        return pinDao.getAllPin()
    }
}