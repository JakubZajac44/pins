package com.jakub.zajac.pin.domain.use_case

import com.jakub.zajac.pin.domain.model.PinModel
import com.jakub.zajac.pin.domain.repository.PinRepository
import javax.inject.Inject

class AddNewPinUseCase @Inject constructor(
    private val pinRepository: PinRepository,
    private val validatePinNameUseCase: ValidatePinNameUseCase
) {
    suspend operator fun invoke(pinCode: String, pinName: String): Int? {
        return when (val validationPinNameResult = validatePinNameUseCase.invoke(pinName)) {
            is PinNameValidationStatus.EmptyPinName -> {
                validationPinNameResult.messageId
            }
            PinNameValidationStatus.ValidPinName ->{
                pinRepository.insertPin(
                    PinModel(
                        pinCode = pinCode, name = pinName
                    )
                )
                    null
            }
        }
    }
}

