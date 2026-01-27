package org.example.bettr.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.example.bettr.designsystem.theme.BettrGray
import org.example.bettr.designsystem.theme.BettrGrayDarker
import org.example.bettr.designsystem.theme.BettrGrayLight
import org.example.bettr.designsystem.theme.BettrGreen
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.example.bettr.designsystem.theme.BettrTextStyles
import org.example.bettr.designsystem.theme.BettrTheme
import org.example.bettr.designsystem.util.InputFormatter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import bettr.composeapp.generated.resources.Res
import bettr.composeapp.generated.resources.pig_icon

enum class BettrInputType {
    /**
     * Input de texto genérico
     */
    Text,

    /**
     * Input para email
     * - Teclado com @ e .com
     */
    Email,

    /**
     * Input para senha
     * - Teclado de texto
     * - Caracteres ocultos
     */
    Password,

    /**
     * Input para valores monetários (R$)
     * - Teclado numérico com decimais
     */
    Currency,

    /**
     * Input para datas (DD/MM/AAAA)
     * - Teclado numérico
     */
    Date,

    /**
     * Input para números
     * - Teclado numérico
     */
    Number
}

/**
 * Cor de fundo do BettrTextField
 */
enum class BettrTextFieldColor {
    /**
     * Fundo branco
     */
    White,

    /**
     * Fundo cinza claro (neutral)
     */
    Gray
}

@Composable
fun BettrTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    inputType: BettrInputType = BettrInputType.Text,
    color: BettrTextFieldColor = BettrTextFieldColor.White,
    leadingIcon: DrawableResource? = null,
    trailingIcon: DrawableResource? = null,
    enabled: Boolean = true
) {
    val keyboardType = when (inputType) {
        BettrInputType.Text -> KeyboardType.Text
        BettrInputType.Email -> KeyboardType.Email
        BettrInputType.Password -> KeyboardType.Password
        BettrInputType.Currency -> KeyboardType.Decimal
        BettrInputType.Date -> KeyboardType.Number
        BettrInputType.Number -> KeyboardType.Number
    }

    val visualTransformation = when (inputType) {
        BettrInputType.Password -> PasswordVisualTransformation()
        BettrInputType.Date -> InputFormatter.dateVisualTransformation
        BettrInputType.Currency -> InputFormatter.currencyVisualTransformation
        else -> VisualTransformation.None
    }

    val backgroundColor = when (color) {
        BettrTextFieldColor.White -> Color.White
        BettrTextFieldColor.Gray -> BettrNeutralBackground
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        placeholder = {
            Text(
                text = placeholder,
                style = BettrTextStyles.bodyLarge(),
                color = BettrGray
            )
        },
        leadingIcon = if (leadingIcon != null) {
            {
                Image(
                    painter = painterResource(leadingIcon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(BettrGray)
                )
            }
        } else null,
        trailingIcon = if (trailingIcon != null) {
            {
                Image(
                    painter = painterResource(trailingIcon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(BettrGray)
                )
            }
        } else null,
        textStyle = BettrTextStyles.bodyLarge(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BettrGreen,
            unfocusedBorderColor = BettrGrayLight,
            disabledBorderColor = BettrGrayLight,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
            cursorColor = BettrGreen,
            focusedTextColor = BettrGrayDarker,
            unfocusedTextColor = BettrGrayDarker,
            disabledTextColor = BettrGray
        )
    )
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldWhitePreview() {
    BettrTheme {
        BettrTextField(
            value = "",
            onValueChange = {},
            placeholder = "Digite seu email",
            inputType = BettrInputType.Email,
            color = BettrTextFieldColor.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldGrayPreview() {
    BettrTheme {
        BettrTextField(
            value = "",
            onValueChange = {},
            placeholder = "Digite seu email",
            inputType = BettrInputType.Email,
            color = BettrTextFieldColor.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldWithLeadingIconPreview() {
    BettrTheme {
        BettrTextField(
            value = "",
            onValueChange = {},
            placeholder = "R$ 0,00",
            inputType = BettrInputType.Currency,
            leadingIcon = Res.drawable.pig_icon
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldWithTrailingIconPreview() {
    BettrTheme {
        BettrTextField(
            value = "senha123",
            onValueChange = {},
            placeholder = "Senha",
            inputType = BettrInputType.Password,
            trailingIcon = Res.drawable.pig_icon
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldWithValuePreview() {
    BettrTheme {
        BettrTextField(
            value = "usuario@email.com",
            onValueChange = {},
            placeholder = "Email",
            inputType = BettrInputType.Email
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldDisabledPreview() {
    BettrTheme {
        BettrTextField(
            value = "Texto desabilitado",
            onValueChange = {},
            placeholder = "Placeholder",
            enabled = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldDatePreview() {
    BettrTheme {
        BettrTextField(
            value = "25122026",
            onValueChange = {},
            placeholder = "DD/MM/AAAA",
            inputType = BettrInputType.Date
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BettrTextFieldCurrencyPreview() {
    BettrTheme {
        BettrTextField(
            value = "150000",
            onValueChange = {},
            placeholder = "R$ 0,00",
            inputType = BettrInputType.Currency,
            leadingIcon = Res.drawable.pig_icon
        )
    }
}

