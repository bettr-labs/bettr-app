package org.example.bettr.presentation.welcome

fun interface WelcomeAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnClickGetStarted : Action()
    }
}