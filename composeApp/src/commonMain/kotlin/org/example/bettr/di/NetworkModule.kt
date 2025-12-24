package org.example.bettr.di

import io.ktor.client.HttpClient
import org.example.bettr.data.network.BettrClient
import org.example.bettr.data.network.HttpClientFactory
import org.example.bettr.data.repository.OnboardingRepository
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { HttpClientFactory.create() }
    single { BettrClient(get()) }

    // Repositories
    single { OnboardingRepository(get()) }
}

