package org.example.bettr.presentation.bettypes.state

import org.example.bettr.presentation.bettypes.model.BetTypesUiModel

internal sealed class BetTypesUiState {
    data object Loading : BetTypesUiState()
    data class Resumed(val model: BetTypesUiModel) : BetTypesUiState()
}