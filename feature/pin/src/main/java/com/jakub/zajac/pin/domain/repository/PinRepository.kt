package com.jakub.zajac.pin.domain.repository

import com.jakub.zajac.pin.domain.model.PinModel
import kotlinx.coroutines.flow.Flow

interface PinRepository {

    suspend fun insertPin(pinModel: PinModel)

    suspend fun updatePin(pinModel: PinModel)

    suspend fun deletePin(pinModel: PinModel)

    fun getAllPin(): Flow<List<PinModel>>
}