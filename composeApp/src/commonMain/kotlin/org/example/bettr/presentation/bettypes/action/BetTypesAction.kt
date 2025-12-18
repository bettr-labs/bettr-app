package org.example.bettr.presentation.bettypes.action

import org.example.bettr.domain.model.BetType

internal fun interface BetTypesAction {
    fun sendAction(action: Action)
    sealed class Action {
        data object OnInit : Action()
        data object OnClickBack : Action()
        data object OnClickContinue : Action()
        data class OnToggleBetType(val betType: BetType) : Action()
    }
}