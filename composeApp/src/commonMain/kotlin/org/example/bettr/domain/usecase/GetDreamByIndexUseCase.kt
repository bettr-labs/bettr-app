package org.example.bettr.domain.usecase

import org.example.bettr.data.cache.OnboardingCache
import org.example.bettr.domain.model.DreamTypeModel

/**
 * Use case to get a specific dream by index from cache
 */
class GetDreamByIndexUseCase(
    private val onboardingCache: OnboardingCache
) {
    operator fun invoke(index: Int): DreamTypeModel? {
        return onboardingCache.getDreamByIndex(index)
    }
}

