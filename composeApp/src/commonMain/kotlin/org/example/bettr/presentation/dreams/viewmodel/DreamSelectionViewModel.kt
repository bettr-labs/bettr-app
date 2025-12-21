package org.example.bettr.presentation.dreams.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.bettr.domain.usecase.GetDreamTypesUseCase
import org.example.bettr.presentation.dreams.action.DreamSelectionAction
import org.example.bettr.presentation.dreams.model.DreamSelectionItemUiModel
import org.example.bettr.presentation.dreams.model.DreamSelectionUiModel
import org.example.bettr.presentation.dreams.state.DreamSelectionUiState

internal class DreamSelectionViewModel(
    private val getDreamTypesUseCase: GetDreamTypesUseCase
) : ViewModel(), DreamSelectionAction {
    private val _uiState = MutableStateFlow<DreamSelectionUiState>(DreamSelectionUiState.Loading)
    val uiState = _uiState.asStateFlow()

    override fun sendAction(action: DreamSelectionAction.Action) {
        when (action) {
            is DreamSelectionAction.Action.OnInit -> onInit()
        }
    }

    private fun onInit() {
        viewModelScope.launch {
            getDreamTypesUseCase()
                .onSuccess { dreamTypes ->
                    val items = dreamTypes.map { dreamType ->
                        DreamSelectionItemUiModel(
                            type = dreamType.type,
                            label = dreamType.label,
                            isSelected = false
                        )
                    }
                    _uiState.value = DreamSelectionUiState.Resumed(
                        model = DreamSelectionUiModel(items = items)
                    )
                }
                .onFailure {
                    // TODO: Handle error state
                }
        }
    }
}