package org.example.bettr.presentation.dreamsettings.effect

internal sealed class DreamSettingsUiEffect {
    data object NavigateBack : DreamSettingsUiEffect()
    // TODO: Add more effects as needed (e.g., ShowSuccessMessage, NavigateToNextScreen, etc.)
}

