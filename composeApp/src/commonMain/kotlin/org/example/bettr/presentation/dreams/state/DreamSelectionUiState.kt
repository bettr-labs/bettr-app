package org.example.bettr.presentation.dreams.state

import org.example.bettr.presentation.dreams.model.DreamSelectionUiModel

internal sealed class DreamSelectionUiState {
    data object Loading : DreamSelectionUiState()
    data class Resumed(val model: DreamSelectionUiModel) : DreamSelectionUiState()
}