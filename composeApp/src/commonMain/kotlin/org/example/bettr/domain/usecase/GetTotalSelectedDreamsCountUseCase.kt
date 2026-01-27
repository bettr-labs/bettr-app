package org.example.bettr.domain.usecase

import org.example.bettr.data.cache.OnboardingCache

/**
 * Use case to get the total count of selected dreams
 */
class GetTotalSelectedDreamsCountUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(): Int {
        return onboardingCache.getTotalSelectedDreamsCount()
    }
}

