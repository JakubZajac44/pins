package com.jakub.zajac.pin.presentation.pin_list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jakub.zajac.pin.domain.model.PinModel
import com.jakub.zajac.resource.SideEffect
import com.jakub.zajac.resource.SingleEventEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinListScreen(
    modifier: Modifier = Modifier,
    event: (PinListEvent) -> Unit,
    pinList: List<PinModel>,
    sideEffect: Flow<SideEffect>,
) {

    var pinModelIdToEdit: Int? by remember {
        mutableStateOf(null)
    }

    val sheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()


    val context = LocalContext.current
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
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            contentPadding = PaddingValues(vertical = 20.dp)
        ) {


            items(items = pinList, key = { it.id!! }) { pin ->

                SwipeToDeleteContainer(item = pin, onDelete = {
                    event.invoke(PinListEvent.DeletePinTyped(pin.id))
                }) { language ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clickable {
                            isSheetOpen = true
                            pinModelIdToEdit = language.id

                        }) {
                        Text(text = "Pin: ${language.pinCode}, name: ${language.name}, id: ${language.id}")
                    }

                }


            }
        }






        FloatingActionButton(
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

                    pinModelIdToEdit?.let { id ->
                        event.invoke(PinListEvent.UpdateNewPinType(id, "Dupa"))
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


@Composable
fun <T> SwipeToDeleteContainer(
    item: T, onDelete: (T) -> Unit, animationDuration: Int = 500, content: @Composable (T) -> Unit
) {
    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(confirmValueChange = { value ->
        if (value == SwipeToDismissBoxValue.EndToStart) {
            isRemoved = true
            true
        } else {
            false
        }
    })

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }

    AnimatedVisibility(
        visible = !isRemoved, exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration), shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                DeleteBackground(swipeDismissState = state)
            },
            enableDismissFromStartToEnd = false,
            content = { content(item) },
        )
    }
}

@Composable
fun DeleteBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = if (swipeDismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        Color.Red
    } else Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.White
        )
    }
}