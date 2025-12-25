package org.example.bettr.data.network.api

import org.example.bettr.data.network.dto.DreamTypeDto
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

interface OnboardingApi {
    suspend fun getDreamTypes(): Result<List<DreamTypeDto>, NetworkError>
}

