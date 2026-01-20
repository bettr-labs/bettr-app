package org.example.bettr.presentation.dreamsettings.action

internal fun interface DreamSettingsAction {
    fun sendAction(action: Action)

    sealed class Action {
        data object OnInit : Action()
        data class OnValueChanged(val value: String) : Action()
        data class OnDateChanged(val date: String) : Action()
        data object OnSaveClicked : Action()
    }
}

