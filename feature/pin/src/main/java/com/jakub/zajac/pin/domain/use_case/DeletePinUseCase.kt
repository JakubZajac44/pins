package com.jakub.zajac.pin.domain.use_case

import com.jakub.zajac.pin.domain.model.PinModel
import com.jakub.zajac.pin.domain.repository.PinRepository
import javax.inject.Inject

class DeletePinUseCase @Inject constructor(
    private val pinRepository: PinRepository
) {
    suspend operator fun invoke(pinModel: PinModel) {
        pinRepository.deletePin(pinModel)
    }
}