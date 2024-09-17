package com.jakub.zajac.pin.presentation

sealed class PinListEvent {
    data class AddNewPinTyped(val pinName: String) : PinListEvent()
    data class DeletePinTyped(val id: Int?) : PinListEvent()
    data class UpdateNewPinType(val id: Int?, val name: String): PinListEvent()
}