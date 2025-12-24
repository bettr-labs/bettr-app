package org.example.bettr.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.bettr.designsystem.theme.BettrGreen
import org.example.bettr.designsystem.theme.BettrNeutralBackground
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BettrLoading() {
    Box(
        modifier = Modifier.fillMaxSize().background(BettrNeutralBackground),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = BettrGreen
        )
    }
}

@Preview
@Composable
fun BettrLoadingPreview() {
    BettrLoading()
}