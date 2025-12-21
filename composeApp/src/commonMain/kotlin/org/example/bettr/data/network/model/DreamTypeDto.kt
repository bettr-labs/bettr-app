package org.example.bettr.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DreamTypeDto(
    @SerialName("key") val key: String,
    @SerialName("label") val label: String
)

