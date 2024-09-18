package com.jakub.zajac.pin.domain.use_case

import com.jakub.zajac.pin.domain.model.PinModel
import com.jakub.zajac.pin.domain.repository.PinRepository
import javax.inject.Inject

class UpdatePinUseCase @Inject constructor(
    private val pinRepository: PinRepository,
    private val validatePinNameUseCase: ValidatePinNameUseCase
) {
    suspend operator fun invoke(pinModel: PinModel): Int? {

        return when (val validationPinNameResult = validatePinNameUseCase.invoke(pinModel.name)) {
            is PinNameValidationStatus.EmptyPinName -> {
                validationPinNameResult.messageId
            }
            PinNameValidationStatus.ValidPinName ->{
                pinRepository.updatePin(
                    pinModel
                )
                null
            }
        }
    }
}