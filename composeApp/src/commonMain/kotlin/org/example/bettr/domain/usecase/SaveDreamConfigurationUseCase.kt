package org.example.bettr.domain.usecase

import org.example.bettr.data.cache.DreamConfigurationModel
import org.example.bettr.data.cache.OnboardingCache

/**
 * Use case to save dream configuration to cache
 */
class SaveDreamConfigurationUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(index: Int, targetAmount: Double, targetDate: String) {
        val configuration = DreamConfigurationModel(
            targetAmount = targetAmount,
            targetDate = targetDate
        )
        onboardingCache.saveDreamConfiguration(index, configuration)
    }
}

