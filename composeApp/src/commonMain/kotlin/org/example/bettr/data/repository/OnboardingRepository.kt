package org.example.bettr.data.repository

import org.example.bettr.data.network.api.OnboardingApi
import org.example.bettr.data.network.model.DreamTypeDto

class OnboardingRepository(
    private val onboardingApi: OnboardingApi
) {
    suspend fun getDreamTypes(): Result<List<DreamTypeDto>> {
        return try {
            val dreamTypes = onboardingApi.getDreamTypes()
            Result.success(dreamTypes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

