package org.example.bettr.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.house_icon
import bettr.composeapp.generated.resources.x_icon
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrGreenDark
import org.example.bettr.designsystem.theme.BettrGreenLight
import org.example.bettr.designsystem.theme.BettrGreenLighter
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BettrSelectedListCard(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to BettrNeutralBackground,
                        1.0f to BettrGreenLighter
                    )
                )
            )
            .border(
                width = 1.dp,
                color = BettrGreenLight,
                RoundedCornerShape(20.dp)
            )
            .padding(start = 24.dp, top = 16.dp, bottom = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(BettrGreenLight),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(
                        BettrGreenDark
                    )
                )
            }
            Text(
                text = text,
                style = BettrTextStyles.bodyLarge(),
                color = BettrGrayDarker
            )
        }
        Image(
            painter = painterResource(Res.drawable.x_icon),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
                .clickable(onClick = onClick),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrSelectedListCardPreview() {
    BettrSelectedListCard(
        text = "Comprar um imóvel",
        icon = painterResource(Res.drawable.house_icon),
        onClick = {}
    )
}