package org.example.bettr.data.repository

import org.example.bettr.data.network.BettrClient
import org.example.bettr.data.network.model.DreamTypeDto
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

class OnboardingRepository(
    private val bettrClient: BettrClient
) {
    suspend fun getDreamTypes(): Result<List<DreamTypeDto>, NetworkError> {
        return bettrClient.getDreams()
    }
}

