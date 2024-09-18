package com.jakub.zajac.pin.presentation.pin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jakub.zajac.pin.domain.use_case.AddNewPinUseCase
import com.jakub.zajac.pin.domain.use_case.DeletePinUseCase
import com.jakub.zajac.pin.domain.use_case.GenerateRandomPinUseCase
import com.jakub.zajac.pin.domain.use_case.GetAllPinsUseCase
import com.jakub.zajac.pin.domain.use_case.UpdatePinUseCase
import com.jakub.zajac.resource.SideEffect
import com.jakub.zajac.resource.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val getAllPinsUseCase: GetAllPinsUseCase,
    private val addNewPinUseCase: AddNewPinUseCase,
    private val deletePinUseCase: DeletePinUseCase,
    private val updatePinUseCase: UpdatePinUseCase,
    private val generateRandomPinUseCase: GenerateRandomPinUseCase
) : ViewModel() {

    val state = getAllPinsUseCase.invoke().map {
        it.map { item ->
            item.toPinItemState()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private val _sideEffectChannel = Channel<SideEffect>(capacity = Channel.BUFFERED)
    val sideEffectFlow: Flow<SideEffect>
        get() = _sideEffectChannel.receiveAsFlow()


    fun onEvent(event: PinListEvent) {
        when (event) {
            is PinListEvent.AddNewPinTyped -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val errorMessage =
                        addNewPinUseCase.invoke(generateRandomPinUseCase.invoke(), event.pinName)
                    errorMessage?.let { errorResource ->
                        _sideEffectChannel.trySend(
                            SideEffect.ShowToast(
                                UiText.StringResource(
                                    errorResource
                                )
                            )
                        )
                    }
                }
            }

            is PinListEvent.DeletePinTyped -> {
                state.value.firstOrNull { it.id == event.id }?.let { pin ->
                    viewModelScope.launch(Dispatchers.IO) {
                        deletePinUseCase.invoke(pin.toPinModel())
                    }
                }
            }

            is PinListEvent.UpdateNewPinType -> {
                state.value.firstOrNull { it.id == event.id }?.let { pin ->
                    viewModelScope.launch(Dispatchers.IO) {
                        val errorMessage = updatePinUseCase.invoke(
                            pin.toPinModel().copy(
                                name = event.name
                            )
                        )
                        errorMessage?.let { errorResource ->
                            _sideEffectChannel.trySend(
                                SideEffect.ShowToast(
                                    UiText.StringResource(
                                        errorResource
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}