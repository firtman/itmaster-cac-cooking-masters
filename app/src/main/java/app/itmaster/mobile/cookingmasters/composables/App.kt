package app.itmaster.mobile.cookingmasters.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.itmaster.mobile.cookingmasters.composables.details.Details
import app.itmaster.mobile.cookingmasters.composables.home.Home
import app.itmaster.mobile.cookingmasters.composables.shared.Footer
import app.itmaster.mobile.cookingmasters.composables.shared.Header

@Composable
fun App() {

    val navController = rememberNavController()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Header()
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                Home(navController)
            }
            composable("details/{recipe}",
                arguments = listOf(navArgument("recipe") { type = NavType.StringType })
                ) {
                Details(it.arguments?.getString("recipe"), navController)
            }
        }
        Footer()
    }

}