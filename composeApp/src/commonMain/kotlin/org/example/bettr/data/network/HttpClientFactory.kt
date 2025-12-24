package org.example.bettr.data.network

import io.ktor.client.HttpClient

expect object HttpClientFactory {
    fun create(): HttpClient
}

