package org.example.bettr.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual object HttpClientFactory {
    actual fun create(): HttpClient {
        return HttpClient(Darwin) {
            engine {
                configureRequest {
                    setTimeoutInterval(30.0)
                }
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                )
            }
        }
    }
}

