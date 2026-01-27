package org.example.bettr.designsystem.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object InputFormatter {
    /**
     * Remove apenas dígitos da string
     */
    fun getOnlyDigits(text: String): String = text.filter { it.isDigit() }

    /**
     * Formata data como DD/MM/AAAA
     * Input: "25122026" -> Output: "25/12/2026"
     */
    fun formatDate(text: String): String {
        val digits = getOnlyDigits(text)
        if (digits.isEmpty()) return ""

        return when {
            digits.length <= 2 -> digits
            digits.length <= 4 -> "${digits.substring(0, 2)}/${digits.substring(2)}"
            else -> {
                val day = digits.substring(0, 2)
                val month = digits.substring(2, 4)
                val year = digits.substring(4, minOf(8, digits.length))
                "$day/$month/$year"
            }
        }
    }

    /**
     * Formata moeda como R$ 1.000,00
     * Input: "150000" -> Output: "R$ 1.500,00"
     */
    fun formatCurrency(text: String): String {
        val digits = getOnlyDigits(text)
        if (digits.isEmpty()) return "R$ 0,00"

        val value = digits.toLongOrNull() ?: return "R$ 0,00"
        val cents = value % 100
        val reais = value / 100

        val reaisFormatted = reais.toString().reversed()
            .chunked(3)
            .joinToString(".")
            .reversed()

        return "R$ $reaisFormatted,${"$cents".padStart(2, '0')}"
    }

    /**
     * VisualTransformation para data (DD/MM/AAAA)
     */
    val dateVisualTransformation = object : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val digits = getOnlyDigits(text.text).take(8)
            val formatted = formatDate(digits)

            val offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    // Mapear posição no texto original (só dígitos) para posição no texto formatado (com "/")
                    if (offset <= 0) return 0
                    if (offset <= 2) return offset // Posições 0-2 mapeiam direto
                    if (offset <= 4) return offset + 1 // Após "25/", adiciona 1
                    if (offset <= 8) return offset + 2 // Após "25/12/", adiciona 2
                    return formatted.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    // Mapear posição no texto formatado para posição no texto original
                    var digitCount = 0
                    for (i in 0 until minOf(offset, formatted.length)) {
                        if (formatted[i].isDigit()) digitCount++
                    }
                    return minOf(digitCount, digits.length)
                }
            }

            return TransformedText(AnnotatedString(formatted), offsetMapping)
        }
    }

    /**
     * VisualTransformation para moeda (R$ 1.000,00)
     */
    val currencyVisualTransformation = object : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            val digits = getOnlyDigits(text.text)
            val formatted = formatCurrency(text.text)

            val offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset > digits.length) return formatted.length

                    // Contar quantos dígitos temos até esse offset
                    var digitCount = 0
                    for (i in 0 until minOf(offset, text.text.length)) {
                        if (text.text[i].isDigit()) digitCount++
                    }

                    // Encontrar a posição no texto formatado
                    var pos = 0
                    var count = 0
                    for (i in formatted.indices) {
                        if (formatted[i].isDigit()) {
                            if (count == digitCount) return i
                            count++
                        }
                    }
                    return formatted.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    var digitCount = 0
                    for (i in 0 until minOf(offset, formatted.length)) {
                        if (formatted[i].isDigit()) digitCount++
                    }
                    return minOf(digitCount, digits.length)
                }
            }

            return TransformedText(AnnotatedString(formatted), offsetMapping)
        }
    }
}