package org.example.bettr.presentation.openfinance.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.open_finance_img
import org.example.bettr.designsystem.components.BettrButton
import org.example.bettr.designsystem.components.BettrButtonColor
import org.example.bettr.designsystem.components.BettrButtonSize
import org.example.bettr.designsystem.components.BettrPagination
import org.example.bettr.designsystem.components.BettrStaticListCard
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun OpenFinanceScreen(

) {
    OpenFinanceScreenContent()
}

@Composable
private fun OpenFinanceScreenContent(
) {
    Scaffold(
        modifier = Modifier.background(BettrNeutralBackground),
        topBar = {
            BettrPagination(
                modifier = Modifier.padding(horizontal = 24.dp)
                    .padding(top = 52.dp, bottom = 24.dp),
                totalPages = 4,
                currentPage = 4
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
                    text = "Conectar conta seguramente",
                    size = BettrButtonSize.SmallText,
                    enabled = true,
                    onClick = { }
                )
                Spacer(Modifier.height(12.dp))
                BettrButton(
                    text = "Vou anotar manualmente por enquanto",
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
                text = "Vamos automatizar sua jornada?",
                style = BettrTextStyles.headlineMedium(),
                textAlign = TextAlign.Center,
                color = BettrGrayDarker
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Deixe a tecnologia trabalhar a seu favor",
                style = BettrTextStyles.bodyLarger(),
                textAlign = TextAlign.Center,
                color = BettrGrayDark
            )
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(Res.drawable.open_finance_img),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Conectando sua conta bancária via Open Finance, nós identificamos automaticamente transferências para sites de apostas.",
                        style = BettrTextStyles.bodyLarge(),
                        color = BettrGrayDark
                    )
                    Text(
                        text = "Isso te ajuda a ver o impacto real no seu sonho, sem você precisar anotar nada manualmente.",
                        style = BettrTextStyles.bodyLarge(),
                        color = BettrGrayDark
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            OpenFinanceCardsList()
        }
    }
}

@Composable
private fun OpenFinanceCardsList(

) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BettrStaticListCard(
            title = "Automático e preciso",
            description = "Rastreamento inteligente de transações"
        )
        BettrStaticListCard(
            title = "100% seguro",
            description = "Tecnologia aprovada pelo Banco Central"
        )
        BettrStaticListCard(
            title = "Privacidade total",
            description = "Apenas você vê suas informações"
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun BetTypesScreenPreview() {
    OpenFinanceScreenContent(
    )
}