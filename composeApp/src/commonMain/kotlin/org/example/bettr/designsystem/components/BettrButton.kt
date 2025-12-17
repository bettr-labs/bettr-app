package org.example.bettr.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGreen
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.designsystem.theme.BettrTheme
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class BettrButtonSize {
    LargeText,
    SmallText
}

enum class BettrButtonColor {
    Green,
    GrayDark,
    Neutral
}

@Composable
fun BettrButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: BettrButtonSize = BettrButtonSize.LargeText,
    color: BettrButtonColor = BettrButtonColor.Green
) {
    val backgroundColor = when (color) {
        BettrButtonColor.Green -> BettrGreen
        BettrButtonColor.GrayDark -> BettrGrayDark
        BettrButtonColor.Neutral -> BettrNeutralBackground
    }

    val textColor = when (color) {
        BettrButtonColor.Green -> BettrNeutralBackground
        BettrButtonColor.GrayDark -> BettrNeutralBackground
        BettrButtonColor.Neutral -> BettrGrayDark
    }

    val disabledBackgroundColor = backgroundColor.copy(alpha = 0.5f)
    val disabledContentColor = textColor.copy(alpha = 0.5f)

    val textStyle = when (size) {
        BettrButtonSize.LargeText -> BettrTextStyles.titleSmall()
        BettrButtonSize.SmallText -> BettrTextStyles.labelLarge()
    }

    val buttonHeight = when (size) {
        BettrButtonSize.LargeText -> 56.dp
        BettrButtonSize.SmallText -> 48.dp
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight),
        enabled = enabled,
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor
        )
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrButtonGreenLargePreview() {
    BettrTheme {
        BettrButton(
            text = "Continuar",
            onClick = {},
            size = BettrButtonSize.LargeText,
            color = BettrButtonColor.Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrButtonGreenSmallPreview() {
    BettrTheme {
        BettrButton(
            text = "Continuar",
            onClick = {},
            size = BettrButtonSize.SmallText,
            color = BettrButtonColor.Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrButtonGrayDarkLargePreview() {
    BettrTheme {
        BettrButton(
            text = "Continuar",
            onClick = {},
            size = BettrButtonSize.LargeText,
            color = BettrButtonColor.GrayDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrButtonGrayDarkSmallPreview() {
    BettrTheme {
        BettrButton(
            text = "Continuar",
            onClick = {},
            size = BettrButtonSize.SmallText,
            color = BettrButtonColor.GrayDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrButtonDisabledGreenPreview() {
    BettrTheme {
        BettrButton(
            text = "Continuar",
            onClick = {},
            enabled = false,
            color = BettrButtonColor.Green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrButtonDisabledGrayPreview() {
    BettrTheme {
        BettrButton(
            text = "Continuar",
            onClick = {},
            enabled = false,
            color = BettrButtonColor.GrayDark
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrButtonNeutralPreview() {
    BettrTheme {
        BettrButton(
            text = "Pular",
            onClick = {},
            color = BettrButtonColor.Neutral
        )
    }
}

