package org.example.bettr.presentation.bettypes.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.bettr.domain.model.BetType
import org.example.bettr.presentation.bettypes.action.BetTypesAction
import org.example.bettr.presentation.bettypes.effect.BetTypesUiEffect
import org.example.bettr.presentation.bettypes.model.BetTypesUiModel
import org.example.bettr.presentation.bettypes.state.BetTypesUiState
import org.example.bettr.presentation.bettypes.view.getMockBetTypes

internal class BetTypesViewModel : ViewModel(), BetTypesAction {
    private val _uiState = MutableStateFlow<BetTypesUiState>(BetTypesUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _uiEffect = MutableSharedFlow<BetTypesUiEffect>(replay = 0, extraBufferCapacity = 1)
    val uiEffect = _uiEffect.asSharedFlow()

    override fun sendAction(action: BetTypesAction.Action) {
        when (action) {
            is BetTypesAction.Action.OnInit -> onInit()
            is BetTypesAction.Action.OnClickBack -> onBackClick()
            is BetTypesAction.Action.OnClickContinue -> onContinueClick()
            is BetTypesAction.Action.OnToggleBetType -> onToggleBetType(action.betType)
        }
    }

    private fun onInit() {
        // TODO: Replace with backend call when ready
        val mockBetTypes = getMockBetTypes()
        _uiState.value = BetTypesUiState.Resumed(model = BetTypesUiModel(items = mockBetTypes))
    }

    private fun onToggleBetType(betType: BetType) {
        val currentState = _uiState.value
        if (currentState is BetTypesUiState.Resumed) {
            val updatedItems = currentState.model.items.map { item ->
                if (item.type == betType) {
                    item.copy(isSelected = !item.isSelected)
                } else {
                    item
                }
            }
            _uiState.update {
                BetTypesUiState.Resumed(model = currentState.model.copy(items = updatedItems))
            }
        }
    }

    private fun onContinueClick() {
        // TODO: Handle continue with selected bet types
        _uiEffect.tryEmit(BetTypesUiEffect.NavigateToNextScreen)
    }

    private fun onBackClick() {
        _uiEffect.tryEmit(BetTypesUiEffect.NavigateBack)
    }
}