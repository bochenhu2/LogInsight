package com.bochen.loginsight.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bochen.loginsight.ui.screens.LogDetails.LogDetailScreen
import com.bochen.loginsight.ui.screens.MainScreen.MainScreen
import com.bochen.loginsight.viewmodel.MainViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    /*val context = LocalContext.current

    val database = remember {
        LogDatabase.getInstance(context)
    }

    val repository = remember {
        LogRepository(
            logDao = database.logDao(),
            logApi = RetrofitClient.logApi
        )
    }

    val factory = remember {
        `MainViewModelFactory.bat`(repository)
    }

    val viewModel: MainViewModel = viewModel(factory=factory)*/
    val viewModel: MainViewModel= hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = MainDestination.route
    ) {
        composable(route = MainDestination.route) {
            MainScreen (viewModel,
                onNavigateToDetails = {level ->
                    navController.navigate(LogDetailsDestination.makeRoute(level))
                }
            )
        }

        composable(route = LogDetailsDestination.route,
            arguments = LogDetailsDestination.arguments) {
            backStackEntry ->
            val res = backStackEntry.arguments?.getString(LogDetailsDestination.LEVEL_ARG) ?: "UNKNOWN"

            LogDetailScreen(viewModel,
                msg = res,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}