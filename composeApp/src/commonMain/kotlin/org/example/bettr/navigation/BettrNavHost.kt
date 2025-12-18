package org.example.bettr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.example.bettr.presentation.bettypes.view.BetTypesScreen
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
                    // TODO: Navigate to next screen when implemented
                }
            )
        }
    }
}

