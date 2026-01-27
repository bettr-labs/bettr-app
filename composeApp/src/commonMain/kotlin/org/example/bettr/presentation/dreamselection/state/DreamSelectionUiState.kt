package org.example.bettr.presentation.dreamselection.state

import org.example.bettr.presentation.dreamselection.model.DreamSelectionUiModel

internal sealed class DreamSelectionUiState {
    data object Loading : DreamSelectionUiState()
    data class Resumed(val model: DreamSelectionUiModel) : DreamSelectionUiState()
    data class Error(val message: String) : DreamSelectionUiState()
}
