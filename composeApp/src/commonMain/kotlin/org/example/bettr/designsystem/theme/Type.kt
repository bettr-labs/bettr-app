package org.example.bettr.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.inter_regular
import bettr.composeapp.generated.resources.inter_medium
import bettr.composeapp.generated.resources.inter_semibold
import bettr.composeapp.generated.resources.inter_bold
import org.jetbrains.compose.resources.Font

@Composable
fun InterFontFamily() = FontFamily(
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_semibold, FontWeight.SemiBold),
    Font(Res.font.inter_bold, FontWeight.Bold)
)

@Composable
fun BettrTypography(): Typography {
    val interFamily = InterFontFamily()

    return Typography(
        displayLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 57.sp
        ),
        displayMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp
        ),
        displaySmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 30.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp
        ),
        titleLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp
        ),
        titleMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        titleSmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        bodySmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        labelLarge = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        labelMedium = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        labelSmall = TextStyle(
            fontFamily = interFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp
        )
    )
}

// Custom text styles - use BettrTextStyles instead of MaterialTheme.typography
object BettrTextStyles {
    @Composable
    fun displayLarge() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp
    )

    @Composable
    fun displayMedium() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp
    )

    @Composable
    fun displaySmall() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    )

    @Composable
    fun headlineLarge() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    )

    @Composable
    fun headlineMedium() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp
    )

    @Composable
    fun headlineSmall() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    )

    @Composable
    fun titleLarge() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    )

    @Composable
    fun titleMedium() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    )

    @Composable
    fun titleSmall() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    )

    @Composable
    fun bodyLarger() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )

    @Composable
    fun bodyLarge() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

    @Composable
    fun bodyMedium() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )

    @Composable
    fun bodySmall() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )

    @Composable
    fun labelLarge() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )

    @Composable
    fun labelSmall() = TextStyle(
        fontFamily = InterFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
}

