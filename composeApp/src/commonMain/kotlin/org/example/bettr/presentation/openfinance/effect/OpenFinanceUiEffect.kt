package org.example.bettr.presentation.openfinance.effect

internal sealed class OpenFinanceUiEffect {
    data object NavigateToNextScreen : OpenFinanceUiEffect()
}