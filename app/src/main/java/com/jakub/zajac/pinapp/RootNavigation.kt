package com.jakub.zajac.pinapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jakub.zajac.pin.presentation.PIN_GRAPH_ROUTE
import com.jakub.zajac.pin.presentation.pinGraph

@Composable
fun RootNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = PIN_GRAPH_ROUTE
    ) {
        pinGraph(
            navController = navController
        )
    }
}