package org.example.bettr.presentation.bettypes.effect

internal sealed class BetTypesUiEffect {
    data object NavigateBack : BetTypesUiEffect()
    data object NavigateToNextScreen : BetTypesUiEffect()
}