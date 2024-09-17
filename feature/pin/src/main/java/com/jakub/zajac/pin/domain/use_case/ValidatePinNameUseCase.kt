package com.jakub.zajac.pin.domain.use_case

import com.jakub.zajac.pin.R
import javax.inject.Inject

class ValidatePinNameUseCase @Inject constructor() {

    operator fun invoke(pinName: String): PinNameValidationStatus {
        return if (pinName.isEmpty()) PinNameValidationStatus.EmptyPinName
        else PinNameValidationStatus.ValidPinName
    }
}

sealed class PinNameValidationStatus(val messageId: Int?) {
    data object EmptyPinName : PinNameValidationStatus(R.string.pin_empty_name_error)
    data object ValidPinName : PinNameValidationStatus(null)
}