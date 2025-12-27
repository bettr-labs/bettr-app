package org.example.bettr.data.cache

import org.example.bettr.domain.model.DreamTypeModel

/**
 * In-memory cache for onboarding flow state.
 * Singleton that persists user selections and configurations during the onboarding process.
 */
class OnboardingCache {

    private var selectedDreams: List<DreamTypeModel> = emptyList()
    private var configuredDreams: MutableMap<Int, DreamConfigurationModel> = mutableMapOf()

    /**
     * Store the dreams selected by the user
     */
    fun setSelectedDreams(dreams: List<DreamTypeModel>) {
        selectedDreams = dreams
        // Initialize empty configurations for each dream
        configuredDreams.clear()
        dreams.forEachIndexed { index, _ ->
            configuredDreams[index] = DreamConfigurationModel()
        }
    }

    /**
     * Get all selected dreams
     */
    fun getSelectedDreams(): List<DreamTypeModel> = selectedDreams

    /**
     * Get a specific dream by its index
     */
    fun getDreamByIndex(index: Int): DreamTypeModel? {
        return selectedDreams.getOrNull(index)
    }

    /**
     * Get the total count of selected dreams
     */
    fun getTotalSelectedDreamsCount(): Int = selectedDreams.size

    /**
     * Save configuration for a specific dream
     */
    fun saveDreamConfiguration(index: Int, configuration: DreamConfigurationModel) {
        configuredDreams[index] = configuration
    }

    /**
     * Get configuration for a specific dream
     */
    fun getDreamConfiguration(index: Int): DreamConfigurationModel? {
        return configuredDreams[index]
    }

    /**
     * Get all configured dreams ready for submission to backend
     */
    fun getAllConfiguredDreams(): List<ConfiguredDreamModel> {
        return selectedDreams.mapIndexed { index, dream ->
            ConfiguredDreamModel(
                dreamType = dream,
                configuration = configuredDreams[index] ?: DreamConfigurationModel()
            )
        }
    }

    /**
     * Check if all dreams have been fully configured
     */
    fun areAllDreamsConfigured(): Boolean {
        return configuredDreams.size == selectedDreams.size &&
               configuredDreams.values.all { it.isComplete() }
    }

    /**
     * Clear all cached onboarding data
     * Call this after successful submission or when user cancels onboarding
     */
    fun clear() {
        selectedDreams = emptyList()
        configuredDreams.clear()
    }
}

/**
 * Represents the configuration for a single dream
 */
data class DreamConfigurationModel(
    val targetAmount: Double? = null,
    val targetDate: String? = null // TODO: Consider using kotlinx.datetime.LocalDate
) {
    fun isComplete(): Boolean = targetAmount != null && targetDate != null
}

/**
 * Represents a dream with its configuration, ready for POST to backend
 */
data class ConfiguredDreamModel(
    val dreamType: DreamTypeModel,
    val configuration: DreamConfigurationModel
)

