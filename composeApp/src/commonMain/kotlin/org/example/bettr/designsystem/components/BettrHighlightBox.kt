package org.example.bettr.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.bettr.designsystem.theme.BettrBlue
import org.example.bettr.designsystem.theme.BettrBlueDark
import org.example.bettr.designsystem.theme.BettrBlueLight
import org.example.bettr.designsystem.theme.BettrGreenDark
import org.example.bettr.designsystem.theme.BettrGreenLight
import org.example.bettr.designsystem.theme.BettrGreenLighter
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.designsystem.theme.BettrTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class BettrHighlightBoxColor {
    Green,
    Blue
}

@Composable
fun BettrHighlightBox(
    text: String,
    modifier: Modifier = Modifier,
    color: BettrHighlightBoxColor = BettrHighlightBoxColor.Green
) {
    val (gradientStart, gradientEnd, borderColor, textColor) = when (color) {
        BettrHighlightBoxColor.Green -> listOf(
            BettrGreenLighter,
            BettrGreenLight,
            BettrGreenLight,
            BettrGreenDark
        )
        BettrHighlightBoxColor.Blue -> listOf(
            BettrBlueLight,
            BettrBlue,
            BettrBlue,
            BettrBlueDark
        )
    }

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to gradientStart,
                        1.0f to gradientEnd
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp)
    ) {
        Text(
            text = text,
            style = BettrTextStyles.bodyLarge(),
            textAlign = TextAlign.Center,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrHighlightBoxGreenPreview() {
    BettrTheme {
        BettrHighlightBox(
            text = "Milhares de pessoas já retomaram o controle de suas vidas.",
            color = BettrHighlightBoxColor.Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrHighlightBoxBluePreview() {
    BettrTheme {
        BettrHighlightBox(
            text = "Milhares de pessoas já retomaram o controle de suas vidas.",
            color = BettrHighlightBoxColor.Blue
        )
    }
}

