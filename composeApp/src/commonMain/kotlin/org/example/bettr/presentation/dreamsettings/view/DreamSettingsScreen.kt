package org.example.bettr.presentation.dreamsettings.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.dream_settings_currency_placeholder
import bettr.composeapp.generated.resources.dream_settings_date_placeholder
import bettr.composeapp.generated.resources.dream_settings_description
import bettr.composeapp.generated.resources.dream_settings_dream_date_description
import bettr.composeapp.generated.resources.dream_settings_dream_date_title
import bettr.composeapp.generated.resources.dream_settings_dream_value_description
import bettr.composeapp.generated.resources.dream_settings_dream_value_title
import bettr.composeapp.generated.resources.dream_settings_highlight
import bettr.composeapp.generated.resources.dream_settings_save_button
import bettr.composeapp.generated.resources.pig_icon
import org.jetbrains.compose.resources.stringResource
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrButtonColor
import org.example.bettr.designsystem.components.BettrButtonSize
import org.example.bettr.designsystem.components.BettrDreamSettingsInputCard
import org.example.bettr.designsystem.components.BettrGenericError
import org.example.bettr.designsystem.components.BettrHighlightBox
import org.example.bettr.designsystem.components.BettrInputType
import org.example.bettr.designsystem.components.BettrLoading
import org.example.bettr.designsystem.components.BettrPagination
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrGreenDark
import org.example.bettr.designsystem.theme.BettrGreenLighter
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.presentation.dreamsettings.action.DreamSettingsAction
import org.example.bettr.presentation.dreamsettings.effect.DreamSettingsUiEffect
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
) {
    val viewModel: DreamSettingsViewModel = koinInject { parametersOf(currentIndex) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(currentIndex) {
        viewModel.sendAction(DreamSettingsAction.Action.OnInit)
    }

    EffectsHandler(
        viewModel = viewModel,
        onNavigateToNextDream = onNavigateToNextDream,
        onNavigateToNextScreen = onNavigateToNextScreen
    )

    when (val state = uiState) {
        is DreamSettingsUiState.Loading -> {
            BettrLoading()
        }

        is DreamSettingsUiState.Resumed -> {
            val uiModel = state.model
            DreamSettingsContent(
                dreamLabel = uiModel.label,
                value = uiModel.value,
                date = uiModel.date,
                onValueChanged = { newValue ->
                    viewModel.sendAction(DreamSettingsAction.Action.OnValueChanged(newValue))
                },
                onDateChanged = { newDate ->
                    viewModel.sendAction(DreamSettingsAction.Action.OnDateChanged(newDate))
                },
                onSaveClicked = {
                    keyboardController?.hide()
                    viewModel.sendAction(DreamSettingsAction.Action.OnSaveClicked)
                }
            )
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
    dreamLabel: String,
    value: String,
    date: String,
    onValueChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
) {
    val isButtonEnabled = value.isNotEmpty()

    Scaffold(
        topBar = {
            BettrPagination(
                modifier = Modifier.padding(horizontal = 24.dp)
                    .padding(top = 52.dp, bottom = 24.dp),
                totalPages = 4,
                currentPage = 3
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
            ) {
                BettrButton(
                    text = stringResource(Res.string.dream_settings_save_button),
                    size = BettrButtonSize.SmallText,
                    color = BettrButtonColor.GrayDark,
                    enabled = isButtonEnabled,
                    onClick = onSaveClicked
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(horizontal = 24.dp)
        ) {
            val dream = dreamLabel.lowercase()
            Text(
                text = buildAnnotatedString {
                    append("Quanto custa seu sonho de ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(dream)
                    }
                    append("?")
                },
                style = BettrTextStyles.headlineSmall(),
                color = BettrGrayDarker,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.dream_settings_description),
                style = BettrTextStyles.bodyLarge(),
                color = BettrGrayDarker,
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (date.isNotEmpty() && value.isNotEmpty()) {
                DaysRemainingCard(
                    date = date,
                    value = value
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            BettrDreamSettingsInputCard(
                title = stringResource(Res.string.dream_settings_dream_value_title),
                value = value,
                onValueChange = onValueChanged,
                icon = Res.drawable.pig_icon,
                description = stringResource(Res.string.dream_settings_dream_value_description),
                placeholder = stringResource(Res.string.dream_settings_currency_placeholder),
                inputType = BettrInputType.Currency
            )
            Spacer(modifier = Modifier.height(24.dp))
            BettrDreamSettingsInputCard(
                title = stringResource(Res.string.dream_settings_dream_date_title),
                value = date,
                onValueChange = onDateChanged,
                description = stringResource(Res.string.dream_settings_dream_date_description),
                placeholder = stringResource(Res.string.dream_settings_date_placeholder),
                inputType = BettrInputType.Date
            )
            Spacer(modifier = Modifier.height(24.dp))
            BettrHighlightBox(
                text = stringResource(Res.string.dream_settings_highlight),
            )
        }
    }
}

@Composable
private fun DaysRemainingCard(
    date: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BettrGreenLighter
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Para alcançar sua meta até $date, você precisa economizar:",
                style = BettrTextStyles.bodyLarge(),
                color = BettrGrayDark
            )
            // TODO calcular dias restantes para cálculo do valor por dia e dias restantes
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = value,
                    style = BettrTextStyles.titleSmall(),
                    color = BettrGreenDark
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "por dia",
                    style = BettrTextStyles.bodyMedium(),
                    color = BettrGrayDark
                )
            }
            if (date.isNotEmpty()) {
                Text(
                    text = "Faltam $date dias para sua data alvo",
                    style = BettrTextStyles.bodyMedium(),
                    color = BettrGrayDark
                )
            }
        }
    }
}

@Composable
private fun EffectsHandler(
    viewModel: DreamSettingsViewModel,
    onNavigateToNextDream: () -> Unit,
    onNavigateToNextScreen: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is DreamSettingsUiEffect.NavigateToNextScreen -> onNavigateToNextScreen()
                is DreamSettingsUiEffect.NavigateToNextDream -> onNavigateToNextDream()
                else -> Unit
            }
        }
    }
}

@Preview
@Composable
private fun DreamSettingsContentBeforeInputPreview() {
    DreamSettingsContent(
        dreamLabel = "Comprar um imóvel",
        value = "",
        date = "",
        onValueChanged = {},
        onDateChanged = {},
        onSaveClicked = {}
    )
}

@Preview
@Composable
private fun DreamSettingsContentAfterInputPreview() {
    DreamSettingsContent(
        dreamLabel = "Comprar um imóvel",
        value = "50000000",
        date = "120130",
        onValueChanged = {},
        onDateChanged = {},
        onSaveClicked = {}
    )
}