package org.example.bettr

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.example.bettr.navigation.BettrNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true)
fun App() {
    val navController = rememberNavController()
    BettrNavHost(navController = navController)
}