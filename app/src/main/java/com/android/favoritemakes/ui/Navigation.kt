package com.android.favoritemakes.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.favoritemakes.home.FavoriteMakesScreen
import com.android.favoritemakes.home.FavoriteMakesViewModel
import com.android.favoritemakes.makeslist.MakesListScreen
import com.android.favoritemakes.makeslist.MakesListViewModel

@Composable
fun NavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = FavoriteMakes.route,
        modifier = modifier,
    ) {
        composable(
            route = FavoriteMakes.route,
        ) {
            val viewModel: FavoriteMakesViewModel = hiltViewModel()
            FavoriteMakesScreen(
                viewModel = viewModel,
            ) {
                navController.navigate(MakesList.route)
            }
        }
        composable(
            route = MakesList.route,
        ) {
            val viewModel: MakesListViewModel = hiltViewModel()
            MakesListScreen(
                viewModel = viewModel,
            )
        }
    }
}

interface Destination {
    val route: String
}

object FavoriteMakes : Destination {
    override val route = "favorite_makes"
}

object MakesList : Destination {
    override val route = "makes_list"
}