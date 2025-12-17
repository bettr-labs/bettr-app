package org.example.bettr.presentation.bettypes.model

import org.example.bettr.domain.model.BetType
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal data class BetTypeItemUiModel(
    val type: BetType,
    val label: StringResource,
    val icon: DrawableResource,
    val isSelected: Boolean = false
)

