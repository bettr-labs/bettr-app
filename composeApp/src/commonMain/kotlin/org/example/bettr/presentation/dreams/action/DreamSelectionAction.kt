package org.example.bettr.presentation.dreams.action

internal fun interface DreamSelectionAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnInit : Action()
    }
}