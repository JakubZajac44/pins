package com.jakub.zajac.pin.presentation.pin_list

sealed class PinListEvent {
    data class AddNewPinTyped(val pinName: String) : PinListEvent()
    data class DeletePinTyped(val id: Int?) : PinListEvent()
    data class UpdatePinType(val id: Int?, val name: String): PinListEvent()
}