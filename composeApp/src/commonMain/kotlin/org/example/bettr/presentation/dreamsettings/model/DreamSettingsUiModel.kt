package org.example.bettr.presentation.dreamsettings.model

import org.example.bettr.domain.model.DreamType

internal data class DreamSettingsUiModel(
    val dreamType: DreamType,
    val label: String,
    val value: String,
    val date: String,
    val totalDreams: Int
)

