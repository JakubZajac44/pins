package com.jakub.zajac.pin.data.mapper

import com.jakub.zajac.pin.domain.model.PinModel
import com.jakub.zajac.storage.model.PinEntity

fun PinEntity.toPinModel() = PinModel(
    id = this.key,
    name = this.name,
    pinCode = this.pinCode
)

fun PinModel.toPinEntity() = PinEntity(
    key = this.id,
    name = this.name,
    pinCode = this.pinCode
)