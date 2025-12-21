package org.example.bettr.data.network.api

import de.jensklingenberg.ktorfit.http.GET
import org.example.bettr.data.network.model.DreamTypeDto

interface OnboardingApi {

    @GET("dreams-types")
    suspend fun getDreamTypes(): List<DreamTypeDto>
}

