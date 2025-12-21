package org.example.bettr.presentation.dreams.model

import org.example.bettr.domain.model.DreamType

internal data class DreamSelectionItemUiModel(
    val type: DreamType,
    val label: String,
    val isSelected: Boolean = false
)

