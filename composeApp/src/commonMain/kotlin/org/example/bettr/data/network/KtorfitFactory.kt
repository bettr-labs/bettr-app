package org.example.bettr.data.network

import de.jensklingenberg.ktorfit.Ktorfit

object KtorfitFactory {

    private const val BASE_URL = "https://bettr-production.up.railway.app/"

    fun create(): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(BASE_URL)
            .httpClient(HttpClientFactory.create())
            .build()
    }
}

