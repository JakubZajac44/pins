package com.jakub.zajac.pin.presentation.pin_list

import com.jakub.zajac.pin.domain.model.PinModel

data class PinItemState(
    val id: Int, val pinCode: String, val pinName: String
)

fun PinItemState.toPinModel() = PinModel(
    id = this.id, pinCode = this.pinCode, name = this.pinName
)

fun PinModel.toPinItemState() = PinItemState(
    id = this.id!!,
    pinCode = this.pinCode,
    pinName =  this.name
)