package org.example.bettr.presentation.bettypes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.back
import bettr.composeapp.generated.resources.bet_types_casino
import bettr.composeapp.generated.resources.bet_types_highlight
import bettr.composeapp.generated.resources.bet_types_lottery
import bettr.composeapp.generated.resources.bet_types_other
import bettr.composeapp.generated.resources.bet_types_paragraph
import bettr.composeapp.generated.resources.bet_types_poker
import bettr.composeapp.generated.resources.bet_types_sports
import bettr.composeapp.generated.resources.bet_types_tiger
import bettr.composeapp.generated.resources.bet_types_title
import bettr.composeapp.generated.resources.continue_button
import bettr.composeapp.generated.resources.warning_icon
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrButtonColor
import org.example.bettr.designsystem.components.BettrButtonSize
import org.example.bettr.designsystem.components.BettrChecklistCard
import org.example.bettr.designsystem.components.BettrHighlightBox
import org.example.bettr.designsystem.components.BettrHighlightBoxColor
import org.example.bettr.designsystem.components.BettrLoading
import org.example.bettr.designsystem.components.BettrPagination
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.domain.model.BetType
import org.example.bettr.presentation.bettypes.action.BetTypesAction
import org.example.bettr.presentation.bettypes.effect.BetTypesUiEffect
import org.example.bettr.presentation.bettypes.mapper.toIcon
import org.example.bettr.presentation.bettypes.model.BetTypeItemUiModel
import org.example.bettr.presentation.bettypes.state.BetTypesUiState
import org.example.bettr.presentation.bettypes.viewmodel.BetTypesViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
internal fun BetTypesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToNextScreen: () -> Unit,
    viewModel: BetTypesViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendAction(BetTypesAction.Action.OnInit)
    }

    EffectsHandler(viewModel, onNavigateBack, onNavigateToNextScreen)

    when (val state = uiState) {
        is BetTypesUiState.Loading -> BettrLoading()
        is BetTypesUiState.Resumed -> {
            BetTypesScreenContent(
                betTypes = state.model.items,
                onToggleBetType = { betType ->
                    viewModel.sendAction(BetTypesAction.Action.OnToggleBetType(betType))
                },
                onContinueClick = {
                    viewModel.sendAction(BetTypesAction.Action.OnClickContinue)
                },
                onBackClick = {
                    viewModel.sendAction(BetTypesAction.Action.OnClickBack)
                }
            )
        }
    }
}

@Composable
private fun BetTypesScreenContent(
    betTypes: List<BetTypeItemUiModel>,
    onToggleBetType: (BetType) -> Unit,
    onContinueClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(BettrNeutralBackground),
        topBar = {
            BettrPagination(
                modifier = Modifier.padding(horizontal = 24.dp)
                    .padding(top = 52.dp, bottom = 24.dp),
                totalPages = 4,
                currentPage = 1
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(all = 24.dp),
            ) {
                BettrButton(
                    text = stringResource(Res.string.continue_button),
                    size = BettrButtonSize.SmallText,
                    onClick = onContinueClick
                )
                Spacer(Modifier.height(12.dp))
                BettrButton(
                    text = stringResource(Res.string.back),
                    color = BettrButtonColor.Neutral,
                    size = BettrButtonSize.SmallText,
                    onClick = onBackClick
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(Res.string.bet_types_title),
                style = BettrTextStyles.headlineMedium(),
                textAlign = TextAlign.Center,
                color = BettrGrayDarker
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.bet_types_paragraph),
                style = BettrTextStyles.bodyLarger(),
                textAlign = TextAlign.Center,
                color = BettrGrayDark
            )
            Spacer(modifier = Modifier.height(24.dp))
            BettrHighlightBox(
                text = stringResource(Res.string.bet_types_highlight),
                color = BettrHighlightBoxColor.Blue,
                icon = Res.drawable.warning_icon
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                betTypes.forEach { betType ->
                    BettrChecklistCard(
                        text = stringResource(betType.label),
                        icon = betType.icon,
                        checked = betType.isSelected,
                        onClick = { onToggleBetType(betType.type) }
                    )
                }
            }
        }
    }
}

@Composable
private fun EffectsHandler(
    viewModel: BetTypesViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToNextScreen: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is BetTypesUiEffect.NavigateBack -> onNavigateBack()
                is BetTypesUiEffect.NavigateToNextScreen -> onNavigateToNextScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BetTypesScreenPreview() {
    BetTypesScreenContent(
        betTypes = getMockBetTypes(),
        onToggleBetType = {},
        onContinueClick = {},
        onBackClick = {}
    )
}

// TODO: Make getMockBetTypes() private when backend is ready
internal fun getMockBetTypes(): List<BetTypeItemUiModel> = listOf(
    BetTypeItemUiModel(
        type = BetType.SPORT,
        label = Res.string.bet_types_sports,
        icon = BetType.SPORT.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.TIGER,
        label = Res.string.bet_types_tiger,
        icon = BetType.TIGER.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.CASINO,
        label = Res.string.bet_types_casino,
        icon = BetType.CASINO.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.POKER,
        label = Res.string.bet_types_poker,
        icon = BetType.POKER.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.LOTTERY,
        label = Res.string.bet_types_lottery,
        icon = BetType.LOTTERY.toIcon()
    ),
    BetTypeItemUiModel(
        type = BetType.OTHER,
        label = Res.string.bet_types_other,
        icon = BetType.OTHER.toIcon()
    )
)