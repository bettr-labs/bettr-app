package org.example.bettr.presentation.dreams.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result
import org.example.bettr.domain.model.DreamType
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
            is DreamSelectionAction.Action.OnItemClicked -> handleItemClick(action.dreamType)
        }
    }

    private fun onInit() {
        viewModelScope.launch {
            when (val result = getDreamTypesUseCase()) {
                is Result.Success -> {
                    val items = result.data.map { dreamType ->
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
                is Result.Error -> {
                    val errorMessage = when (result.error) {
                        NetworkError.NO_INTERNET -> "Sem conexão com a internet"
                        NetworkError.SERVER_ERROR -> "Erro no servidor"
                        NetworkError.SERIALIZATION -> "Erro ao processar dados"
                        NetworkError.REQUEST_TIMEOUT -> "Tempo de requisição esgotado"
                        NetworkError.UNAUTHORIZED -> "Não autorizado"
                        NetworkError.CONFLICT -> "Conflito na requisição"
                        NetworkError.TOO_MANY_REQUESTS -> "Muitas requisições"
                        NetworkError.PAYLOAD_TOO_LARGE -> "Dados muito grandes"
                        NetworkError.UNKNOWN -> "Erro desconhecido"
                    }
                    _uiState.value = DreamSelectionUiState.Error(message = errorMessage)
                }
            }
        }
    }

    private fun handleItemClick(dreamType: DreamType) {
        val currentState = _uiState.value
        if (currentState is DreamSelectionUiState.Resumed) {
            val currentItems = currentState.model.items
            val selectedCount = currentItems.count { it.isSelected }
            val maxSelection = 3

            val updatedItems = currentItems.map { item ->
                if (item.type == dreamType) {
                    val canToggle = item.isSelected || selectedCount < maxSelection
                    if (canToggle) {
                        item.copy(isSelected = !item.isSelected)
                    } else {
                        item
                    }
                } else {
                    item
                }
            }

            val newSelectedCount = updatedItems.count { it.isSelected }
            val isLimitReached = newSelectedCount >= maxSelection

            val finalItems = updatedItems.map { item ->
                item.copy(visuallyDisabled = if (isLimitReached) true else item.isSelected)
            }

            _uiState.value = DreamSelectionUiState.Resumed(
                model = DreamSelectionUiModel(items = finalItems)
            )
        }
    }
}