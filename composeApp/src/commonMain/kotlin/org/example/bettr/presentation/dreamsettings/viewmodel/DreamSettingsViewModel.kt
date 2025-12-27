package org.example.bettr.presentation.dreamsettings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.bettr.domain.usecase.GetDreamByIndexUseCase
import org.example.bettr.domain.usecase.GetDreamConfigurationUseCase
import org.example.bettr.domain.usecase.SaveDreamConfigurationUseCase
import org.example.bettr.presentation.dreamsettings.action.DreamSettingsAction
import org.example.bettr.presentation.dreamsettings.effect.DreamSettingsUiEffect
import org.example.bettr.presentation.dreamsettings.model.DreamSettingsUiModel
import org.example.bettr.presentation.dreamsettings.state.DreamSettingsUiState

internal class DreamSettingsViewModel(
    private val currentIndex: Int,
    private val getDreamByIndexUseCase: GetDreamByIndexUseCase,
    private val getDreamConfigurationUseCase: GetDreamConfigurationUseCase,
    private val saveDreamConfigurationUseCase: SaveDreamConfigurationUseCase
) : ViewModel(), DreamSettingsAction {

    private val _uiState = MutableStateFlow<DreamSettingsUiState>(DreamSettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DreamSettingsUiEffect?>(0, 1)
    val uiEffect = _uiEffect.asSharedFlow()

    override fun sendAction(action: DreamSettingsAction.Action) {
        when (action) {
            is DreamSettingsAction.Action.OnInit -> onInit()
            // TODO: Handle other actions like OnSaveConfiguration
        }
    }

    private fun onInit() {
        viewModelScope.launch {
            // Load dream data from cache via use case
            val dream = getDreamByIndexUseCase(currentIndex)
            val configuration = getDreamConfigurationUseCase(currentIndex)

            if (dream != null) {
                _uiState.value = DreamSettingsUiState.Resumed(
                    model = DreamSettingsUiModel(
                        // TODO: Add dream and configuration data to UI model when implementing the form
                    )
                )
            } else {
                _uiState.value = DreamSettingsUiState.Error(
                    message = "Erro ao carregar configurações do sonho"
                )
            }
        }
    }

    /**
     * Save dream configuration via use case
     * Call this when user completes the form on this screen
     */
    fun saveDreamConfiguration(targetAmount: Double, targetDate: String) {
        saveDreamConfigurationUseCase(currentIndex, targetAmount, targetDate)
    }
}
