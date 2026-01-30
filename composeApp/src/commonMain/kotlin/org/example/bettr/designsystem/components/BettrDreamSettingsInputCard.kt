package org.example.bettr.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.pig_icon
import org.example.bettr.designsystem.theme.BettrGrayDark
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.designsystem.theme.BettrTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BettrDreamSettingsInputCard(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    icon: DrawableResource? = null,
    description: String? = null,
    placeholder: String = "",
    inputType: BettrInputType = BettrInputType.Text
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon != null) {
                    Image(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(BettrGrayDark)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = title,
                    style = BettrTextStyles.titleSmall(),
                    color = BettrGrayDark
                )
            }

            if (description != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = BettrTextStyles.bodyMedium(),
                    color = BettrGrayDark
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            BettrTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder,
                inputType = inputType,
                color = BettrTextFieldColor.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BettrDreamSettingsInputCardValuePreview() {
    BettrTheme {
        BettrDreamSettingsInputCard(
            title = "Valor do sonho",
            value = "",
            onValueChange = {},
            icon = Res.drawable.pig_icon,
            description = "Quanto você deseja economizar?",
            placeholder = "R$ 0,00",
            inputType = BettrInputType.Currency
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrDreamSettingsInputCardDatePreview() {
    BettrTheme {
        BettrDreamSettingsInputCard(
            title = "Quando você quer realizar esse sonho?",
            value = "",
            onValueChange = {},
            description = "Opcional, mas ajuda a planejar melhor",
            placeholder = "DD/MM/AAAA",
            inputType = BettrInputType.Date
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrDreamSettingsInputCardWithoutDescriptionPreview() {
    BettrTheme {
        BettrDreamSettingsInputCard(
            title = "Valor do sonho",
            value = "R$ 50.000,00",
            onValueChange = {},
            icon = Res.drawable.pig_icon,
            placeholder = "R$ 0,00",
            inputType = BettrInputType.Currency
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrDreamSettingsInputCardWithoutIconPreview() {
    BettrTheme {
        BettrDreamSettingsInputCard(
            title = "Data da meta",
            value = "25/12/2026",
            onValueChange = {},
            placeholder = "DD/MM/AAAA",
            inputType = BettrInputType.Date
        )
    }
}