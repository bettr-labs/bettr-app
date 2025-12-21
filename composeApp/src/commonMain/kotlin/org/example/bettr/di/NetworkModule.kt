package org.example.bettr.di

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.example.bettr.data.network.HttpClientFactory
import org.example.bettr.data.network.KtorfitFactory
import org.example.bettr.data.network.api.OnboardingApi
import org.example.bettr.data.network.api.createOnboardingApi
import org.example.bettr.data.repository.OnboardingRepository
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> { HttpClientFactory.create() }
    single<Ktorfit> { KtorfitFactory.create() }

    // API Services
    single<OnboardingApi> { get<Ktorfit>().createOnboardingApi() }

    // Repositories
    single { OnboardingRepository(get()) }
}

