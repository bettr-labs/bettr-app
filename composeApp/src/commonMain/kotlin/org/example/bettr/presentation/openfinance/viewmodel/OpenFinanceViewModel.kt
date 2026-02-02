package org.example.bettr.presentation.openfinance.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.example.bettr.presentation.openfinance.action.OpenFinanceAction
import org.example.bettr.presentation.openfinance.effect.OpenFinanceUiEffect

internal class OpenFinanceViewModel() : ViewModel(), OpenFinanceAction {

    private val _uiEffect = MutableSharedFlow<OpenFinanceUiEffect>(0, 0)
    val uiEffect = _uiEffect.asSharedFlow()

    override fun sendAction(action: OpenFinanceAction.Action) {
        when (action) {
            is OpenFinanceAction.Action.OnClickConnect -> {}
            is OpenFinanceAction.Action.OnClickSkip -> handleOnClickSkip()
        }
    }

    private fun handleOnClickSkip() {
        _uiEffect.tryEmit(OpenFinanceUiEffect.NavigateToNextScreen)
    }
}