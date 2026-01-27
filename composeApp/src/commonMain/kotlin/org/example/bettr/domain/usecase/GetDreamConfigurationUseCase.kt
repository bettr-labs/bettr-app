package org.example.bettr.domain.usecase

import org.example.bettr.data.cache.DreamConfigurationModel
import org.example.bettr.data.cache.OnboardingCache

/**
 * Use case to get dream configuration from cache
 */
class GetDreamConfigurationUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(index: Int): DreamConfigurationModel? {
        return onboardingCache.getDreamConfiguration(index)
    }
}

