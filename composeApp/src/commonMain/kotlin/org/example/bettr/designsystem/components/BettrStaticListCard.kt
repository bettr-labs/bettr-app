package org.example.bettr.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.bet_sport_img
import bettr.composeapp.generated.resources.check_icon
import bettr.composeapp.generated.resources.house_icon
import org.example.bettr.designsystem.theme.BettrGray
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayLight
import org.example.bettr.designsystem.theme.BettrGreen
import org.example.bettr.designsystem.theme.BettrGreenDark
import org.example.bettr.designsystem.theme.BettrGreenLighter
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.designsystem.theme.BettrTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BettrStaticListCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: DrawableResource? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = BettrGrayLight,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Image(
                    painter = painterResource(it),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = title,
                    style = BettrTextStyles.labelLarge(),
                    color = BettrGrayDark
                )
                if (description != null) {
                    Text(
                        text = description,
                        style = BettrTextStyles.bodyLarge(),
                        color = BettrGrayDark
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BettrStaticListCardPreview() {
    BettrStaticListCard(
        title = "Automático e preciso",
        description = "Rastreamento inteligente de transações",
        icon = Res.drawable.house_icon
    )
}


