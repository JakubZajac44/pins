package com.jakub.zajac.pin.presentation

sealed class Route(val route: String) {
    data object PinListRout : Route("pin-list-rout")
}