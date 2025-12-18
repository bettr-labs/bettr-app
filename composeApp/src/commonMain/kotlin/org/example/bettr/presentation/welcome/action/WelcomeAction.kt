package org.example.bettr.presentation.welcome.action

internal fun interface WelcomeAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnClickGetStarted : Action()
    }
}