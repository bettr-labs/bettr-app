package org.example.bettr.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Welcome : Route

    @Serializable
    data object BetTypes : Route

    @Serializable
    data object DreamSelection : Route

    @Serializable
    data class DreamSettings(
        val currentIndex: Int = 0
    ) : Route
}
