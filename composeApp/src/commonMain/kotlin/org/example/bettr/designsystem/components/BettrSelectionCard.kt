package org.example.bettr.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.house_icon
import org.example.bettr.designsystem.theme.BettrGray
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrGrayLight
import org.example.bettr.designsystem.theme.BettrGreenDark
import org.example.bettr.designsystem.theme.BettrGreenLight
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun BettrSelectionCard(
    text: String,
    icon: Painter,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(167.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                width = 1.dp,
                color = if (selected) BettrGrayLight.copy(alpha = 0.5f) else BettrGrayLight,
                RoundedCornerShape(20.dp)
            )
            .clickable(enabled = !selected) { onClick() }
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(if (selected) BettrGrayLight.copy(alpha = 0.5f) else BettrGreenLight),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                colorFilter = if (selected) ColorFilter.tint(BettrGray.copy(alpha = 0.5f)) else ColorFilter.tint(
                    BettrGreenDark
                )
            )
        }
        Text(
            text = text,
            style = BettrTextStyles.labelLarge(),
            color = if (selected) BettrGray.copy(alpha = 0.5f) else BettrGrayDarker,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BettrSelectionCardPreview() {
    BettrSelectionCard(
        text = "Comprar um imóvel",
        icon = painterResource(Res.drawable.house_icon),
        selected = false,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun BettrSelectionCardSelectedPreview() {
    BettrSelectionCard(
        text = "Comprar um imóvel",
        icon = painterResource(Res.drawable.house_icon),
        selected = true,
        onClick = {}
    )
}