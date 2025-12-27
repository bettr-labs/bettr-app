package org.example.bettr.presentation.dreamsettings.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.bettr.designsystem.components.BettrGenericError
import org.example.bettr.designsystem.components.BettrLoading
import org.example.bettr.domain.usecase.GetDreamByIndexUseCase
import org.example.bettr.domain.usecase.GetTotalSelectedDreamsCountUseCase
import org.example.bettr.presentation.dreamsettings.action.DreamSettingsAction
import org.example.bettr.presentation.dreamsettings.effect.DreamSettingsUiEffect
import org.example.bettr.presentation.dreamsettings.model.DreamSettingsUiModel
import org.example.bettr.presentation.dreamsettings.state.DreamSettingsUiState
import org.example.bettr.presentation.dreamsettings.viewmodel.DreamSettingsViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
internal fun DreamSettingsScreen(
    currentIndex: Int,
    onNavigateToNextDream: () -> Unit,
    onNavigateToNextScreen: () -> Unit,
    getDreamByIndexUseCase: GetDreamByIndexUseCase = koinInject(),
    getTotalSelectedDreamsCountUseCase: GetTotalSelectedDreamsCountUseCase = koinInject()
) {
    // Get ViewModel with parameters
    val viewModel: DreamSettingsViewModel = koinInject { parametersOf(currentIndex) }

    val uiState by viewModel.uiState.collectAsState()
    val uiEffect by viewModel.uiEffect.collectAsState(initial = null)

    val dreamType = getDreamByIndexUseCase(currentIndex)
    val totalDreams = getTotalSelectedDreamsCountUseCase()

    // Determine which navigation callback to use based on progress
    val handleContinue: () -> Unit = {
        val nextIndex = currentIndex + 1
        if (nextIndex < totalDreams) {
            onNavigateToNextDream()
        } else {
            onNavigateToNextScreen()
        }
    }

    LaunchedEffect(currentIndex) {
        viewModel.sendAction(DreamSettingsAction.Action.OnInit)
    }

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            is DreamSettingsUiEffect.NavigateBack -> {
                // Handle back navigation if needed
            }
            // TODO: Handle other effects
            null -> {}
        }
    }

    when (val state = uiState) {
        is DreamSettingsUiState.Loading -> {
            BettrLoading()
        }

        is DreamSettingsUiState.Resumed -> {
            dreamType?.let {
                DreamSettingsContent(
                    model = state.model,
                    dreamLabel = it.label,
                    currentIndex = currentIndex,
                    totalDreams = totalDreams,
                    onNavigateToNextDream = onNavigateToNextDream,
                    onNavigateToNextScreen = onNavigateToNextScreen,
                    onAction = { action ->
                        viewModel.sendAction(action)
                    }
                )
            }
        }

        is DreamSettingsUiState.Error -> {
            BettrGenericError(
                message = state.message,
                onRetry = {
                    viewModel.sendAction(DreamSettingsAction.Action.OnInit)
                }
            )
        }
    }
}

@Composable
private fun DreamSettingsContent(
    model: DreamSettingsUiModel,
    dreamLabel: String,
    currentIndex: Int,
    totalDreams: Int,
    onNavigateToNextDream: () -> Unit,
    onNavigateToNextScreen: () -> Unit,
    onAction: (DreamSettingsAction.Action) -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // TODO: Implement the UI content
            Text("Dream Settings Screen - TODO\nDream: $dreamLabel\nProgress: ${currentIndex + 1}/$totalDreams")
        }
    }
}

@Preview
@Composable
private fun DreamSettingsContentPreview() {
    DreamSettingsContent(
        model = DreamSettingsUiModel(),
        dreamLabel = "Comprar um imóvel",
        currentIndex = 0,
        totalDreams = 3,
        onNavigateToNextDream = {},
        onNavigateToNextScreen = {},
        onAction = {}
    )
}
