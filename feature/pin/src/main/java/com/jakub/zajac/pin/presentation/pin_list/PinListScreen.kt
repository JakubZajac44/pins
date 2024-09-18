package com.jakub.zajac.pin.presentation.pin_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jakub.zajac.pin.R
import com.jakub.zajac.pin.presentation.component.PinBottomSheet
import com.jakub.zajac.pin.presentation.component.PinBottomSheetMode
import com.jakub.zajac.pin.presentation.component.PinCardItem
import com.jakub.zajac.resource.SideEffect
import com.jakub.zajac.resource.SingleEventEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun PinListScreen(
    modifier: Modifier = Modifier,
    event: (PinListEvent) -> Unit,
    pinList: List<PinItemState>,
    sideEffect: Flow<SideEffect>,
) {
    val context = LocalContext.current

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var bottomSheetType: PinBottomSheetMode? by rememberSaveable {
        mutableStateOf(null)
    }

    SingleEventEffect(sideEffect) { effect ->
        when (effect) {
            is SideEffect.ShowToast -> Toast.makeText(
                context, effect.message.asString(context), Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        if (pinList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 20.dp)
            ) {

                items(items = pinList, key = { it.id }) { pin ->
                    PinCardItem(pinItem = pin, itemClickedUpdate = { item ->
                        bottomSheetType = PinBottomSheetMode.UpdateExistingPin(item)
                        isSheetOpen = true
                    }, itemClickedDelete = { id ->
                        event.invoke(PinListEvent.DeletePinTyped(id))
                    })

                }
            }

        } else {
            Text(
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.tertiary,
                text = stringResource(R.string.no_pin_item)
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            onClick = {
                bottomSheetType = PinBottomSheetMode.InsertNewPin
                isSheetOpen = true
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ) {
            Icon(Icons.Filled.Add, null)
        }

        if (isSheetOpen) {
            bottomSheetType?.let { type ->
                PinBottomSheet(pinBottomSheetMode = type, onPinBottomSheetDismissed = {
                    isSheetOpen = false
                }, updatePinName = { item ->
                    event.invoke(PinListEvent.UpdatePinType(item.id, item.pinName))
                    isSheetOpen = false
                }, insertNewPin = { pinName ->
                    event.invoke(PinListEvent.AddNewPinTyped(pinName))
                    isSheetOpen = false
                })
            }
        }
    }
}




