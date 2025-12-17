package org.example.bettr.di

import org.example.bettr.presentation.welcome.viewmodel.WelcomeViewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModels
    single { WelcomeViewModel() }
}

