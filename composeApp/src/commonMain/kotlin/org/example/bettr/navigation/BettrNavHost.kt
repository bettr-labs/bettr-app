package org.example.bettr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.example.bettr.presentation.bettypes.view.BetTypesScreen
import org.example.bettr.presentation.dreamselection.view.DreamSelectionScreen
import org.example.bettr.presentation.dreamsettings.view.DreamSettingsScreen
import org.example.bettr.presentation.welcome.view.WelcomeScreen

@Composable
fun BettrNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.Welcome
    ) {
        composable<Route.Welcome> {
            WelcomeScreen(
                onNavigateToBetTypes = {
                    navController.navigate(Route.BetTypes)
                }
            )
        }
        composable<Route.BetTypes> {
            BetTypesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToNextScreen = {
                    navController.navigate(Route.DreamSelection)
                }
            )
        }
        composable<Route.DreamSelection> {
            DreamSelectionScreen(
                onNavigateToNextScreen = {
                    // Navigate to the first dream settings screen
                    navController.navigate(Route.DreamSettings(currentIndex = 0))
                }
            )
        }
        composable<Route.DreamSettings> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.DreamSettings>()
            val currentIndex = args.currentIndex

            DreamSettingsScreen(
                currentIndex = currentIndex,
                onNavigateToNextDream = {
                    // Navigate to the next dream settings screen
                    // The screen/viewmodel determines if there's a next dream
                    navController.navigate(
                        Route.DreamSettings(currentIndex = currentIndex + 1)
                    )
                },
                onNavigateToNextScreen = {
                    // TODO: Navigate to the next screen after all dreams are configured
                    // For now, just pop back to welcome
                    navController.popBackStack(Route.Welcome, inclusive = false)
                }
            )
        }
    }
}
