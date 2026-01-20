package org.example.bettr.presentation.dreamsettings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.bettr.domain.usecase.GetDreamByIndexUseCase
import org.example.bettr.domain.usecase.GetTotalSelectedDreamsCountUseCase
import org.example.bettr.domain.usecase.SaveDreamConfigurationUseCase
import org.example.bettr.presentation.dreamsettings.action.DreamSettingsAction
import org.example.bettr.presentation.dreamsettings.effect.DreamSettingsUiEffect
import org.example.bettr.presentation.dreamsettings.model.DreamSettingsUiModel
import org.example.bettr.presentation.dreamsettings.state.DreamSettingsUiState

internal class DreamSettingsViewModel(
    private val currentIndex: Int,
    private val getDreamByIndexUseCase: GetDreamByIndexUseCase,
    private val getTotalSelectedDreamsCountUseCase: GetTotalSelectedDreamsCountUseCase,
    private val saveDreamConfigurationUseCase: SaveDreamConfigurationUseCase
) : ViewModel(), DreamSettingsAction {

    private val _uiState = MutableStateFlow<DreamSettingsUiState>(DreamSettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DreamSettingsUiEffect?>(0, 1)
    val uiEffect = _uiEffect.asSharedFlow()

    override fun sendAction(action: DreamSettingsAction.Action) {
        when (action) {
            is DreamSettingsAction.Action.OnInit -> onInit()
            is DreamSettingsAction.Action.OnValueChanged -> onValueChanged(action.value)
            is DreamSettingsAction.Action.OnDateChanged -> onDateChanged(action.date)
            is DreamSettingsAction.Action.OnSaveClicked -> handleOnSaveClicked()
        }
    }

    private fun onInit() {
        viewModelScope.launch {
            val dream = getDreamByIndexUseCase(currentIndex)
            val totalDreams = getTotalSelectedDreamsCountUseCase()
            if (dream != null) {
                _uiState.value = DreamSettingsUiState.Resumed(
                    model = DreamSettingsUiModel(
                        dreamType = dream.type,
                        label = dream.label,
                        value = "",
                        date = "",
                        totalDreams = totalDreams
                    )
                )
            } else {
                _uiState.value = DreamSettingsUiState.Error(
                    message = "Erro ao carregar configurações do sonho"
                )
            }
        }
    }

    private fun onValueChanged(value: String) {
        val currentState = _uiState.value
        if (currentState is DreamSettingsUiState.Resumed) {
            _uiState.value = DreamSettingsUiState.Resumed(
                model = currentState.model.copy(value = value)
            )
        }
    }

    private fun onDateChanged(date: String) {
        val currentState = _uiState.value
        if (currentState is DreamSettingsUiState.Resumed) {
            _uiState.value = DreamSettingsUiState.Resumed(
                model = currentState.model.copy(date = date)
            )
        }
    }

    private fun handleOnSaveClicked() {
        val state = _uiState.value
        if (state is DreamSettingsUiState.Resumed) {
            val model = state.model
            try {
                val targetAmount = model.value.takeIf { it.isNotEmpty() }?.toDoubleOrNull() ?: 0.0
                saveDreamConfigurationUseCase(currentIndex, targetAmount, model.date)
            } catch (e: Exception) {
                // Log error if needed
            }
        }

        val totalDreams = when (val currentState = _uiState.value) {
            is DreamSettingsUiState.Resumed -> currentState.model.totalDreams
            else -> 0
        }

        val nextIndex = currentIndex + 1
        if (nextIndex < totalDreams) {
            _uiEffect.tryEmit(DreamSettingsUiEffect.NavigateToNextDream)
        } else {
            collectAndSendAllDreamConfigurations()
            _uiEffect.tryEmit(DreamSettingsUiEffect.NavigateToNextScreen)
        }
    }

    private fun collectAndSendAllDreamConfigurations() {
        // TODO: Implementar quando endpoint backend existir
        // Coleta todas as configurações de sonhos dos índices 0, 1, 2...
        // e as envia para o backend em uma única requisição
    }
}
