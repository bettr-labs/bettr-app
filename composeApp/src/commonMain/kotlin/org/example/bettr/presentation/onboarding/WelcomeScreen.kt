package org.example.bettr.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.welcome_button
import bettr.composeapp.generated.resources.welcome_highlight
import bettr.composeapp.generated.resources.welcome_icon
import bettr.composeapp.generated.resources.welcome_paragraph_1
import bettr.composeapp.generated.resources.welcome_paragraph_2
import bettr.composeapp.generated.resources.welcome_tagline
import bettr.composeapp.generated.resources.welcome_title
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrHighlightBox
import org.example.bettr.designsystem.components.BettrHighlightBoxColor
import org.example.bettr.designsystem.theme.BettrBlueLight
import org.example.bettr.designsystem.theme.BettrGray
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.designsystem.theme.BettrTheme
import org.example.bettr.designsystem.theme.BettrWhite
import org.example.bettr.designsystem.util.parseBoldText
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
private fun WelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to BettrWhite,
                        0.4f to BettrBlueLight,
                        1.0f to BettrBlueLight
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.welcome_icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                modifier = Modifier.padding(12.dp),
                text = stringResource(Res.string.welcome_title),
                style = BettrTextStyles.headlineMedium(),
                textAlign = TextAlign.Center,
                color = BettrGrayDarker
            )
            Column(
                modifier = Modifier.padding(vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(Res.string.welcome_paragraph_1).parseBoldText(),
                    style = BettrTextStyles.bodyLarger(),
                    textAlign = TextAlign.Center,
                    color = BettrGrayDark
                )
                Text(
                    text = stringResource(Res.string.welcome_paragraph_2),
                    style = BettrTextStyles.bodyLarger(),
                    textAlign = TextAlign.Center,
                    color = BettrGrayDark
                )
            }
            BettrHighlightBox(
                text = stringResource(Res.string.welcome_highlight),
                color = BettrHighlightBoxColor.Green
            )
            Spacer(Modifier.height(48.dp))
            BettrButton(
                text = stringResource(Res.string.welcome_button),
                onClick = { }
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(Res.string.welcome_tagline),
                style = BettrTextStyles.bodyMedium(),
                color = BettrGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    BettrTheme {
        WelcomeScreen()
    }
}