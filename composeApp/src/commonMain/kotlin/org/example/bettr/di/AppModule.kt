package org.example.bettr.di

import org.example.bettr.data.cache.OnboardingCache
import org.example.bettr.domain.usecase.GetDreamByIndexUseCase
import org.example.bettr.domain.usecase.GetDreamConfigurationUseCase
import org.example.bettr.domain.usecase.GetDreamTypesUseCase
import org.example.bettr.domain.usecase.GetTotalSelectedDreamsCountUseCase
import org.example.bettr.domain.usecase.SaveDreamConfigurationUseCase
import org.example.bettr.domain.usecase.SetSelectedDreamsUseCase
import org.example.bettr.presentation.bettypes.viewmodel.BetTypesViewModel
import org.example.bettr.presentation.dreamselection.viewmodel.DreamSelectionViewModel
import org.example.bettr.presentation.dreamsettings.viewmodel.DreamSettingsViewModel
import org.example.bettr.presentation.welcome.viewmodel.WelcomeViewModel
import org.koin.dsl.module

val appModule = module {
    // Cache
    single { OnboardingCache() }

    // Use Cases
    factory { GetDreamTypesUseCase(get()) }
    factory { SetSelectedDreamsUseCase(get()) }
    factory { GetDreamByIndexUseCase(get()) }
    factory { GetTotalSelectedDreamsCountUseCase(get()) }
    factory { SaveDreamConfigurationUseCase(get()) }
    factory { GetDreamConfigurationUseCase(get()) }

    // ViewModels
    single { WelcomeViewModel() }
    factory { BetTypesViewModel() }
    factory { DreamSelectionViewModel(get(), get()) }
    factory { (currentIndex: Int) ->
        DreamSettingsViewModel(
            currentIndex,
            get(),
            get(),
            get()
        )
    }
}
