package com.jakub.zajac.pin.presentation.pin_list

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jakub.zajac.pin.presentation.Route

internal fun NavGraphBuilder.pinListNavigation(
) {
    composable(Route.PinListRout.route) {

        val viewModel: PinViewModel = hiltViewModel()
        PinListScreen(
            event = viewModel::onEvent,
            pinList = viewModel.state.collectAsStateWithLifecycle().value,
            sideEffect = viewModel.sideEffectFlow
        )
    }
}