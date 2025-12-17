package org.example.bettr.presentation.bettypes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.back
import bettr.composeapp.generated.resources.bet_types_highlight
import bettr.composeapp.generated.resources.bet_types_paragraph
import bettr.composeapp.generated.resources.bet_types_title
import bettr.composeapp.generated.resources.continue_button
import bettr.composeapp.generated.resources.warning_icon
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrButtonColor
import org.example.bettr.designsystem.components.BettrButtonSize
import org.example.bettr.designsystem.components.BettrChecklistCard
import org.example.bettr.designsystem.components.BettrHighlightBox
import org.example.bettr.designsystem.components.BettrHighlightBoxColor
import org.example.bettr.designsystem.components.BettrPagination
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.domain.model.BetType
import org.example.bettr.presentation.bettypes.mapper.getMockBetTypes
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun BetTypesScreen(
) {
    // TODO: Replace getMockBetTypes() with ViewModel state when backend is ready
    val betTypes = remember { getMockBetTypes() }
    val selectedTypes = remember { mutableStateListOf<BetType>() }

    Scaffold(
        modifier = Modifier.Companion.background(BettrNeutralBackground),
        topBar = {
            BettrPagination(
                modifier = Modifier.Companion.padding(all = 24.dp),
                totalPages = 4,
                currentPage = 1
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .background(Color.Companion.White)
                    .padding(all = 24.dp),
            ) {
                BettrButton(
                    text = stringResource(Res.string.continue_button),
                    size = BettrButtonSize.SmallText,
                    onClick = { /* TODO: Handle click */ }
                )
                Spacer(Modifier.Companion.height(12.dp))
                BettrButton(
                    text = stringResource(Res.string.back),
                    color = BettrButtonColor.Neutral,
                    size = BettrButtonSize.SmallText,
                    onClick = { /* TODO: Handle click */ }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.Companion.padding(paddingValues).padding(horizontal = 24.dp)
        ) {
            Text(
                modifier = Modifier.Companion.padding(top = 12.dp),
                text = stringResource(Res.string.bet_types_title),
                style = BettrTextStyles.headlineMedium(),
                textAlign = TextAlign.Companion.Center,
                color = BettrGrayDarker
            )
            Spacer(modifier = Modifier.Companion.height(12.dp))
            Text(
                text = stringResource(Res.string.bet_types_paragraph),
                style = BettrTextStyles.bodyLarger(),
                textAlign = TextAlign.Companion.Center,
                color = BettrGrayDark
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            BettrHighlightBox(
                text = stringResource(Res.string.bet_types_highlight),
                color = BettrHighlightBoxColor.Blue,
                icon = Res.drawable.warning_icon
            )
            Spacer(modifier = Modifier.Companion.height(16.dp))
            Column(
                modifier = Modifier.Companion
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                betTypes.forEach { betType ->
                    BettrChecklistCard(
                        text = stringResource(betType.label),
                        icon = betType.icon,
                        checked = selectedTypes.contains(betType.type),
                        onClick = {
                            if (selectedTypes.contains(betType.type)) {
                                selectedTypes.remove(betType.type)
                            } else {
                                selectedTypes.add(betType.type)
                            }
                        }
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun BetTypesScreenPreview() {
    BetTypesScreen()
}