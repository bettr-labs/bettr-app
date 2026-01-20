package org.example.bettr.presentation.dreamsettings.effect

internal sealed class DreamSettingsUiEffect {
    data object NavigateToNextScreen : DreamSettingsUiEffect()
    data object NavigateToNextDream : DreamSettingsUiEffect()
}

