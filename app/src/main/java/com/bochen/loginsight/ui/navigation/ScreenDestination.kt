package com.bochen.loginsight.ui.navigation

import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object MainDestination : Destination {
    override val route = "main"
}

object LogDetailsDestination : Destination {
    private const val BASE_ROUTE = "log_details"
    override val route = "$BASE_ROUTE/{$LEVEL_ARG}"

    const val LEVEL_ARG = "level"

    val arguments = listOf(
        navArgument(LogDetailsDestination.LEVEL_ARG){
            type = NavType.StringType
        }
    )

    fun makeRoute(level: String): String{
        return "$BASE_ROUTE/$level"
    }
}