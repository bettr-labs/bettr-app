package org.example.bettr.presentation.dreams.action

import org.example.bettr.domain.model.DreamType

internal fun interface DreamSelectionAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnInit : Action()
        data class OnItemClicked(val dreamType: DreamType) : Action()
    }
}