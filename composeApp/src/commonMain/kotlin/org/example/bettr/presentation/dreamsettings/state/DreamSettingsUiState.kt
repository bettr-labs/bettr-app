package org.example.bettr.presentation.dreamsettings.state

import org.example.bettr.presentation.dreamsettings.model.DreamSettingsUiModel

internal sealed class DreamSettingsUiState {
    data object Loading : DreamSettingsUiState()
    data class Resumed(val model: DreamSettingsUiModel) : DreamSettingsUiState()
    data class Error(val message: String) : DreamSettingsUiState()
}

