package com.jakub.zajac.pin.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jakub.zajac.pin.presentation.pin_list.pinListNavigation

const val PIN_GRAPH_ROUTE = "pin-graph"


fun NavGraphBuilder.pinGraph(
    navController: NavController,
) {

    navigation(
        route = PIN_GRAPH_ROUTE, startDestination = Route.PinListRout.route
    ) {
        pinListNavigation()

    }
}