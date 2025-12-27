package org.example.bettr.presentation.dreamsettings.action

internal fun interface DreamSettingsAction {
    fun sendAction(action: Action)

    sealed class Action {
        data object OnInit : Action()
        // TODO: Add more actions as needed (e.g., OnSaveClicked, OnCancelClicked, etc.)
    }
}

