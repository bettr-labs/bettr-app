package org.example.bettr.presentation.dreams.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.continue_button
import bettr.composeapp.generated.resources.dream_selection_title
import bettr.composeapp.generated.resources.dream_selection_paragraph
import bettr.composeapp.generated.resources.dream_selection_tagline
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrButtonColor
import org.example.bettr.designsystem.components.BettrButtonSize
import org.example.bettr.designsystem.components.BettrGenericError
import org.example.bettr.designsystem.components.BettrLoading
import org.example.bettr.designsystem.components.BettrPagination
import org.example.bettr.designsystem.components.BettrSelectedListCard
import org.example.bettr.designsystem.components.BettrSelectionCard
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.domain.model.DreamType
import org.example.bettr.presentation.dreams.action.DreamSelectionAction
import org.example.bettr.presentation.dreams.mapper.toIcon
import org.example.bettr.presentation.dreams.model.DreamSelectionItemUiModel
import org.example.bettr.presentation.dreams.state.DreamSelectionUiState
import org.example.bettr.presentation.dreams.viewmodel.DreamSelectionViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
internal fun DreamSelectionScreen(
    viewModel: DreamSelectionViewModel = koinInject(),
    onNavigateBack: () -> Unit,
    onNavigateToNextScreen: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendAction(DreamSelectionAction.Action.OnInit)
    }

    when (uiState) {
        DreamSelectionUiState.Loading -> BettrLoading()
        is DreamSelectionUiState.Resumed -> {
            val items = (uiState as DreamSelectionUiState.Resumed).model.items
            DreamSelectionScreenContent(
                items = items,
                onItemClick = {
                    viewModel.sendAction(DreamSelectionAction.Action.OnItemClicked(it))
                }
            )
        }
        is DreamSelectionUiState.Error -> {
            val errorMessage = (uiState as DreamSelectionUiState.Error).message
            BettrGenericError(
                message = errorMessage,
                onRetry = {
                    viewModel.sendAction(DreamSelectionAction.Action.OnInit)
                }
            )
        }
    }
}

@Composable
private fun DreamSelectionScreenContent(
    items: List<DreamSelectionItemUiModel>,
    onItemClick: (DreamType) -> Unit
) {
    val filteredItems = items.filter { it.type != DreamType.OTHER }

    Scaffold(
        modifier = Modifier.background(BettrNeutralBackground),
        topBar = {
            BettrPagination(
                modifier = Modifier.padding(horizontal = 24.dp)
                    .padding(top = 52.dp, bottom = 24.dp),
                totalPages = 4,
                currentPage = 2
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 24.dp),
            ) {
                BettrButton(
                    text = stringResource(Res.string.continue_button),
                    size = BettrButtonSize.SmallText,
                    color = BettrButtonColor.GrayDark,
                    enabled = items.any { it.isSelected },
                    onClick = {}
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(Res.string.dream_selection_title),
                style = BettrTextStyles.headlineMedium(),
                textAlign = TextAlign.Center,
                color = BettrGrayDarker
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(Res.string.dream_selection_paragraph),
                style = BettrTextStyles.bodyLarger(),
                textAlign = TextAlign.Center,
                color = BettrGrayDark
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = stringResource(Res.string.dream_selection_tagline),
                style = BettrTextStyles.bodyMedium(),
                textAlign = TextAlign.Left,
                color = BettrGrayDark
            )
            Spacer(modifier = Modifier.height(24.dp))

            val selectedItems = filteredItems.filter { it.isSelected }
            if (selectedItems.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    selectedItems.forEach { item ->
                        BettrSelectedListCard(
                            text = item.label,
                            icon = painterResource(item.type.toIcon()),
                            onClick = { onItemClick(item.type) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            DreamSelectionGrid(
                items = filteredItems,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun DreamSelectionGrid(
    items: List<DreamSelectionItemUiModel>,
    onItemClick: (DreamType) -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
            ) {
                rowItems.forEach { item ->
                    BettrSelectionCard(
                        text = item.label,
                        icon = painterResource(item.type.toIcon()),
                        selected = item.visuallyDisabled,
                        onClick = { onItemClick(item.type) }
                    )
                }
            }
        }
    }
}

private fun getMockDreamItems(): List<DreamSelectionItemUiModel> = listOf(
    DreamSelectionItemUiModel(type = DreamType.HOME, label = "Comprar um imóvel", isSelected = false),
    DreamSelectionItemUiModel(type = DreamType.TRAVEL, label = "Viajar", isSelected = false),
    DreamSelectionItemUiModel(type = DreamType.MONEY, label = "Guardar dinheiro", isSelected = true),
    DreamSelectionItemUiModel(type = DreamType.CAR, label = "Comprar um carro", isSelected = false),
    DreamSelectionItemUiModel(type = DreamType.STUDY, label = "Estudar/Curso", isSelected = false),
    DreamSelectionItemUiModel(type = DreamType.WEDDING, label = "Casamento", isSelected = false),
    DreamSelectionItemUiModel(type = DreamType.VACATION, label = "Férias dos sonhos", isSelected = false),
    DreamSelectionItemUiModel(type = DreamType.HEALTH, label = "Saúde/Fitness", isSelected = false)
)

@Preview(showBackground = true)
@Composable
private fun DreamSelectionScreenPreview() {
    DreamSelectionScreenContent(
        items = getMockDreamItems(),
        onItemClick = {}
    )
}