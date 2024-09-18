package com.jakub.zajac.pin.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jakub.zajac.pin.R
import com.jakub.zajac.pin.presentation.pin_list.PinItemState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinBottomSheet(
    pinBottomSheetMode: PinBottomSheetMode,
    onPinBottomSheetDismissed: () -> Unit,
    updatePinName: (PinItemState) -> Unit,
    insertNewPin: (String) -> Unit
) {


    var pinNameText by remember {
        mutableStateOf(
            when (pinBottomSheetMode) {
                PinBottomSheetMode.InsertNewPin -> ""
                is PinBottomSheetMode.UpdateExistingPin -> pinBottomSheetMode.pinItemState.pinName
            }
        )
    }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = {
        onPinBottomSheetDismissed.invoke()
    }) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = stringResource(id = pinBottomSheetMode.title), fontSize = 16.sp)
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = pinNameText,
                onValueChange = { newText ->
                    pinNameText = newText
                },
                label = { Text(text = stringResource(id = pinBottomSheetMode.message)) },
            )
            Spacer(modifier = Modifier.height(30.dp))

            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                when (pinBottomSheetMode) {
                    PinBottomSheetMode.InsertNewPin -> insertNewPin.invoke(pinNameText)
                    is PinBottomSheetMode.UpdateExistingPin -> {
                        updatePinName.invoke(pinBottomSheetMode.pinItemState.copy(pinName = pinNameText))
                    }
                }

                onPinBottomSheetDismissed.invoke()

                scope.launch {
                    sheetState.hide()
                }

            }, enabled = pinNameText.isNotEmpty()) {
                Text(text = stringResource(id = pinBottomSheetMode.buttonText))
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

sealed class PinBottomSheetMode(
    val title: Int, val message: Int, val buttonText: Int = R.string.bottom_sheet_button_text
) {
    data object InsertNewPin : PinBottomSheetMode(
        title = R.string.bottom_sheet_insert_pin_title,
        message = R.string.bottom_sheet_insert_pin_message
    )

    data class UpdateExistingPin(val pinItemState: PinItemState) : PinBottomSheetMode(
        title = R.string.bottom_sheet_update_pin_title,
        message = R.string.bottom_sheet_update_pin_message
    )
}