package org.example.bettr.presentation.welcome

sealed class WelcomeUiEffect {
    data object NavigateToBetTypes : WelcomeUiEffect()
}