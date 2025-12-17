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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.bet_sport_img
import bettr.composeapp.generated.resources.check_icon
import org.example.bettr.designsystem.theme.BettrGray
import org.example.bettr.designsystem.theme.BettrGrayDark
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
fun BettrChecklistCard(
    text: String,
    checked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null
) {
    val backgroundColor = if (checked) BettrGreenLighter else BettrNeutralBackground
    val borderColor = if (checked) BettrGreen else BettrGray.copy(alpha = 0.3f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(22.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Box(modifier = Modifier.size(12.dp))
            }
            Text(
                text = text,
                style = BettrTextStyles.labelLarge(),
                color = if (checked) BettrGreenDark else BettrGrayDark
            )
        }
        if (checked) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (checked) BettrGreen else Color.White)
                    .border(
                        width = 2.dp,
                        color = if (checked) BettrGreen else BettrGray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.check_icon),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BettrChecklistCardUncheckedPreview() {
    BettrTheme {
        BettrChecklistCard(
            text = "Apostas esportivas",
            checked = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrChecklistCardCheckedPreview() {
    BettrTheme {
        BettrChecklistCard(
            text = "Apostas esportivas",
            checked = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrChecklistCardWithIconPreview() {
    BettrTheme {
        BettrChecklistCard(
            text = "Apostas esportivas",
            checked = true,
            onClick = {},
            icon = Res.drawable.bet_sport_img
        )
    }
}


