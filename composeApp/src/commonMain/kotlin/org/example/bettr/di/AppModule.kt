package org.example.bettr.di

import org.example.bettr.domain.usecase.GetDreamTypesUseCase
import org.example.bettr.presentation.bettypes.viewmodel.BetTypesViewModel
import org.example.bettr.presentation.welcome.viewmodel.WelcomeViewModel
import org.koin.dsl.module

val appModule = module {
    // Use Cases
    factory { GetDreamTypesUseCase(get()) }

    // ViewModels
    single { WelcomeViewModel() }
    factory { BetTypesViewModel() }
}

