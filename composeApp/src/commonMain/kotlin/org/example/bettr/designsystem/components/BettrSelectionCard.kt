package org.example.bettr.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.house_icon
import org.example.bettr.designsystem.theme.BettrGray
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrGrayLight
import org.example.bettr.designsystem.theme.BettrGreenDark
import org.example.bettr.designsystem.theme.BettrGreenLight
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun BettrSelectionCard(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null
) {
    Column(
        modifier = Modifier
            .width(167.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = if (selected) BettrGrayLight else BettrGray,
                RoundedCornerShape(20.dp)
            )
            .background(BettrNeutralBackground)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (selected) BettrGrayLight else BettrGreenLight),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.house_icon),
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                colorFilter = if (selected) ColorFilter.tint(BettrGray) else ColorFilter.tint(
                    BettrGreenDark
                )
            )
        }
        Text(
            text = text,
            style = BettrTextStyles.labelSmall(),
            color = if (selected) BettrGray else BettrGrayDarker,
        )
    }
}

@Preview
@Composable
private fun BettrSelectionCardPreview() {
    BettrSelectionCard(
        text = "Comprar um imóvel",
        selected = false,
        onClick = {}
    )
}

@Preview
@Composable
private fun BettrSelectionCardSelectedPreview() {
    BettrSelectionCard(
        text = "Comprar um imóvel",
        selected = true,
        onClick = {}
    )
}