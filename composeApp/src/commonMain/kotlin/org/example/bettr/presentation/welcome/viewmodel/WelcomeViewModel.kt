package org.example.bettr.presentation.welcome.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.example.bettr.presentation.welcome.action.WelcomeAction
import org.example.bettr.presentation.welcome.effect.WelcomeUiEffect

internal class WelcomeViewModel : ViewModel(), WelcomeAction {
    private val _uiEffect = MutableSharedFlow<WelcomeUiEffect>(replay = 0, extraBufferCapacity = 1)
    val uiEffect = _uiEffect.asSharedFlow()

    override fun sendAction(action: WelcomeAction.Action) {
        when (action) {
            is WelcomeAction.Action.OnClickGetStarted -> onGetStartedClick()
        }
    }

    private fun onGetStartedClick() {
        _uiEffect.tryEmit(WelcomeUiEffect.NavigateToBetTypes)
    }
}