package org.example.bettr.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.bettr.data.network.BettrClient
import org.example.bettr.data.network.dto.DreamTypeDto
import org.example.bettr.data.network.util.NetworkError
import org.example.bettr.data.network.util.Result

class OnboardingApiImpl(
    private val httpClient: HttpClient,
    private val bettrClient: BettrClient
) : OnboardingApi {
    private companion object {
        const val BASE_URL = "https://bettr-production.up.railway.app"
    }

    override suspend fun getDreamTypes(): Result<List<DreamTypeDto>, NetworkError> {
        return bettrClient.safeApiCall {
            httpClient.get("$BASE_URL/dreams-types").body()
        }
    }
}


