package org.example.bettr.domain.usecase

import org.example.bettr.data.cache.OnboardingCache
import org.example.bettr.domain.model.DreamTypeModel

/**
 * Use case to store selected dreams in cache during onboarding flow
 */
class SetSelectedDreamsUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(dreams: List<DreamTypeModel>) {
        onboardingCache.setSelectedDreams(dreams)
    }
}

