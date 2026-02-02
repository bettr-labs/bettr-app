package org.example.bettr.presentation.shield.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.shield_img
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrButtonColor
import org.example.bettr.designsystem.components.BettrButtonSize
import org.example.bettr.designsystem.components.BettrHighlightBox
import org.example.bettr.designsystem.components.BettrHighlightBoxColor
import org.example.bettr.designsystem.components.BettrPagination
import org.example.bettr.designsystem.components.BettrStaticListCard
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShieldScreen(
    onNavigateToNextScreen: () -> Unit
) {
    ShieldScreenContent()
}

@Composable
private fun ShieldScreenContent() {
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
                    .background(Color.White)
                    .padding(all = 24.dp),
            ) {
                BettrButton(
                    text = "Ativar escudo protetor",
                    size = BettrButtonSize.SmallText,
                    enabled = true,
                    onClick = { }
                )
                Spacer(Modifier.height(12.dp))
                BettrButton(
                    text = "Pular (não recomendado)",
                    color = BettrButtonColor.Neutral,
                    size = BettrButtonSize.SmallText,
                    onClick = { }
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
                text = "Vamos criar sua primeira barreira",
                style = BettrTextStyles.headlineMedium(),
                textAlign = TextAlign.Center,
                color = BettrGrayDarker
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Para te ajudar nos momentos de impulso, precisamos da sua permissão para detectar e intervir quando você tentar acessar sites ou apps de apostas.",
                style = BettrTextStyles.bodyLarger(),
                textAlign = TextAlign.Center,
                color = BettrGrayDark
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(Res.drawable.shield_img),
                contentDescription = null
            )
            ShieldCardsList()
            Spacer(modifier = Modifier.height(24.dp))
            BettrHighlightBox(
                text = "Precisamos da sua permissão porque essa é a única forma de detectar quando você está abrindo um app ou site específico. Seus dados nunca saem do seu celular.",
                color = BettrHighlightBoxColor.Yellow
            )
        }
    }
}

@Composable
private fun ShieldCardsList(

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BettrStaticListCard(
            title = "Bloqueio de Sites",
            description = "Detecta e bloqueia sites de apostas antes que você acesse",
        )
        BettrStaticListCard(
            title = "Bloqueio de Apps",
            description = "Impede a abertura de aplicativos de apostas instalados",
        )
        BettrStaticListCard(
            title = "Intervenção Imediata",
            description = "Mostra exercícios de respiração antes de permitir acesso",
        )
    }
}

@Preview(heightDp = 1200)
@Composable
private fun ShieldScreenPreview() {
    ShieldScreenContent()
}