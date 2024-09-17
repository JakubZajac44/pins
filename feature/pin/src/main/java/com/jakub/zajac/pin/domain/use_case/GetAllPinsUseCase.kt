package com.jakub.zajac.pin.domain.use_case

import com.jakub.zajac.pin.domain.model.PinModel
import com.jakub.zajac.pin.domain.repository.PinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPinsUseCase @Inject constructor(
    private val pinRepository: PinRepository,
) {
    operator fun invoke(): Flow<List<PinModel>> {
        return pinRepository.getAllPin()
    }
}