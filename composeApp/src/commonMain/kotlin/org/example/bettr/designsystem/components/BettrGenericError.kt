package org.example.bettr.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.warning_icon
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.designsystem.theme.BettrTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BettrGenericError(
    message: String,
    modifier: Modifier = Modifier,
    icon: DrawableResource = Res.drawable.warning_icon,
    onRetry: (() -> Unit)? = null,
    retryButtonText: String = "Tentar novamente"
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            colorFilter = ColorFilter.tint(BettrGrayDark)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = BettrTextStyles.bodyLarge(),
            color = BettrGrayDarker,
            textAlign = TextAlign.Center
        )

        onRetry?.let {
            Spacer(modifier = Modifier.height(24.dp))

            BettrButton(
                text = retryButtonText,
                onClick = it,
                size = BettrButtonSize.SmallText,
                color = BettrButtonColor.GrayDark
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BettrGenericErrorPreview() {
    BettrTheme {
        BettrGenericError(
            message = "Algo deu errado. Por favor, tente novamente.",
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrGenericErrorWithoutRetryPreview() {
    BettrTheme {
        BettrGenericError(
            message = "Não foi possível carregar os dados."
        )
    }
}
