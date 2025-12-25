package org.example.bettr.data.repository

import org.example.bettr.data.network.api.OnboardingApi
import org.example.bettr.data.network.dto.DreamTypeDto
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

class OnboardingRepository(
    private val onboardingApi: OnboardingApi
) {
    suspend fun getDreamTypes(): Result<List<DreamTypeDto>, NetworkError> {
        return onboardingApi.getDreamTypes()
    }
}

