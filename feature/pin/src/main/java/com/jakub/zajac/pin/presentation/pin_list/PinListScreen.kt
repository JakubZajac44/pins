package com.jakub.zajac.pin.presentation.pin_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jakub.zajac.pin.presentation.component.PinCardItem
import com.jakub.zajac.resource.SideEffect
import com.jakub.zajac.resource.SingleEventEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinListScreen(
    modifier: Modifier = Modifier,
    event: (PinListEvent) -> Unit,
    pinList: List<PinItemState>,
    sideEffect: Flow<SideEffect>,
) {
    val context = LocalContext.current

    var pinModelIdToEdit: Int? by remember {
        mutableStateOf(null)
    }

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()


    SingleEventEffect(sideEffect) { effect ->
        when (effect) {
            is SideEffect.ShowToast -> Toast.makeText(
                context, effect.message.asString(context), Toast.LENGTH_SHORT
            ).show()
        }
    }



    Box(modifier = modifier.fillMaxSize()) {


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            items(items = pinList, key = { it.id }) { pin ->
                PinCardItem(pinItem = pin, itemClickedUpdate = { id ->
                    isSheetOpen = true
                    pinModelIdToEdit = id
                }, itemClickedDelete = { id ->
                    event.invoke(PinListEvent.DeletePinTyped(id))
                })

            }
        }




        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { event.invoke(PinListEvent.AddNewPinTyped("Kuba")) },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                },
            ) {
                Text("BottomSheet")
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = {
                    isSheetOpen = false
                    pinModelIdToEdit?.let { id ->
                        event.invoke(PinListEvent.UpdateNewPinType(id, "Dupa"))
                        pinModelIdToEdit = null
                    }
                    scope.launch {
                        sheetState.hide()
                    }

                }) {

                    Text(text = "Zapisz")
                }
            }

        }


    }
}




