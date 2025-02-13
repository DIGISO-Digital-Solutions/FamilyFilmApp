package com.apptolast.familyfilmapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apptolast.familyfilmapp.navigation.navtypes.DetailNavTypeDestination
import com.apptolast.familyfilmapp.navigation.navtypes.SearchNavTypeDestination
import com.apptolast.familyfilmapp.ui.screens.groups.GroupsScreen
import com.apptolast.familyfilmapp.ui.screens.home.HomeScreen
import com.apptolast.familyfilmapp.ui.screens.login.LoginScreen
import com.apptolast.familyfilmapp.ui.screens.movieDetails.DetailsScreenRoot
import com.apptolast.familyfilmapp.ui.screens.profile.ProfileScreen
import com.apptolast.familyfilmapp.ui.screens.recommend.RecommendScreen
import com.apptolast.familyfilmapp.ui.screens.search.SearchScreen
import timber.log.Timber

@Composable
fun AppNavigation(viewModel: AppNavigationViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    val userState by viewModel.userState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = userState) {
        Timber.d("User state change: $userState")
        if (userState != null) {
            navController.navigate(Routes.Home.routes)
        } else {
            navController.popBackStack(
                Routes.Login.routes,
                false,
            )
        }
    }

    NavHost(
        navController = navController,
        modifier = Modifier.padding(),
        startDestination = Routes.Login.routes,
    ) {
        composable(route = Routes.Login.routes) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Routes.Home.routes,
            arguments = listOf(),
        ) {
            HomeScreen(navController = navController)
        }
        composable(route = Routes.Recommend.routes) {
            RecommendScreen(navController = navController)
        }
        composable(route = Routes.Groups.routes) {
            GroupsScreen(navController = navController)
        }
        composable(route = Routes.Profile.routes) {
            ProfileScreen(
                navController = navController,
            )
        }
        composable(
            route = Routes.Details.routes,
            arguments = DetailNavTypeDestination.argumentList,
        ) { backStackEntry ->
            val (movie) = DetailNavTypeDestination.parseArguments(backStackEntry)
            DetailsScreenRoot(
                navController = navController,
                movie = movie,
            )
        }
        composable(
            route = Routes.Search.routes,
            arguments = SearchNavTypeDestination.argumentList,
        ) { backStackEntry ->
            val (groupId) = SearchNavTypeDestination.parseArguments(backStackEntry)
            SearchScreen(
                navController = navController,
                groupId = groupId,
            )
        }
    }
}
