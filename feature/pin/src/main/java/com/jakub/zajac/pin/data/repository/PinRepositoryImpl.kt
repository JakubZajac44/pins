package com.jakub.zajac.pin.data.repository

import com.jakub.zajac.pin.data.data_source.PinLocalDataSource
import com.jakub.zajac.pin.data.mapper.toPinEntity
import com.jakub.zajac.pin.data.mapper.toPinModel
import com.jakub.zajac.pin.domain.model.PinModel
import com.jakub.zajac.pin.domain.repository.PinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PinRepositoryImpl @Inject constructor(
    private val pinLocalDataSource: PinLocalDataSource
) : PinRepository {
    override suspend fun insertPin(pinModel: PinModel) {
        pinLocalDataSource.insertPin(pinModel.toPinEntity())
    }

    override suspend fun updatePin(pinModel: PinModel) {
        pinLocalDataSource.updatePint(pinModel.toPinEntity())
    }

    override suspend fun deletePin(pinModel: PinModel) {
        pinLocalDataSource.deletePin(pinModel.toPinEntity())
    }

    override fun getAllPin(): Flow<List<PinModel>> {
        return pinLocalDataSource.getAllPin().map { list -> list.map { it.toPinModel() } }
    }
}