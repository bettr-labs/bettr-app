package org.example.bettr.designsystem.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

/**
 * Parses a string with <b></b> tags and returns an AnnotatedString with bold styling.
 */
fun String.parseBoldText(): AnnotatedString {
    return buildAnnotatedString {
        var currentIndex = 0
        val boldPattern = Regex("<b>(.*?)</b>")

        boldPattern.findAll(this@parseBoldText).forEach { matchResult ->
            // Append text before the match
            append(this@parseBoldText.substring(currentIndex, matchResult.range.first))

            // Append the bold text
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(matchResult.groupValues[1])
            }

            currentIndex = matchResult.range.last + 1
        }

        // Append remaining text
        if (currentIndex < this@parseBoldText.length) {
            append(this@parseBoldText.substring(currentIndex))
        }
    }
}

