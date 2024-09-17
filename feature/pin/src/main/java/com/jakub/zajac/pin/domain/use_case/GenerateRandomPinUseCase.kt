package com.jakub.zajac.pin.domain.use_case

import javax.inject.Inject

class GenerateRandomPinUseCase @Inject constructor() {
    operator fun invoke(): String {
        return (1..6).map { ('0'..'9').random() }.joinToString("")
    }
}