package org.example.bettr.presentation.openfinance.action

internal fun interface OpenFinanceAction {
    fun sendAction(action: Action)

    sealed class Action {
        data object OnClickConnect : Action()
        data object OnClickSkip : Action()
    }
}