package com.jakub.zajac.pinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jakub.zajac.pin.presentation.PinListScreen
import com.jakub.zajac.pin.presentation.PinViewModel
import com.jakub.zajac.pinapp.ui.theme.PinAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PinAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: PinViewModel = hiltViewModel()
                    PinListScreen(
                        modifier = Modifier.padding(innerPadding),
                        event = viewModel::onEvent,
                        pinList = viewModel.state.collectAsStateWithLifecycle().value,
                        sideEffect = viewModel.sideEffectFlow
                    )
                }
            }
        }
    }
}

