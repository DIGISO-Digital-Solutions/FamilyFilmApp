package com.digitalsolution.familyfilmapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digitalsolution.familyfilmapp.ui.components.BottomBar
import com.digitalsolution.familyfilmapp.ui.components.TopBar
import com.digitalsolution.familyfilmapp.ui.screens.DetailsScreen
import com.digitalsolution.familyfilmapp.ui.screens.filter.FilterScreen
import com.digitalsolution.familyfilmapp.ui.screens.home.HomeScreen
import com.digitalsolution.familyfilmapp.ui.screens.login.LoginScreen
import com.digitalsolution.familyfilmapp.ui.screens.profile.ProfileScreen
import com.digitalsolution.familyfilmapp.ui.screens.recommend.RecommendScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    var isBottomBarVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (isBottomBarVisible) {
                TopBar()
            }
        },
        bottomBar = {
            if (isBottomBarVisible) {
                BottomBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            startDestination = Routes.Login.routes
        ) {
            composable(route = Routes.Login.routes) {
                LoginScreen(navController = navController)
            }
            composable(route = Routes.Home.routes) {
                HomeScreen(navController = navController)
            }
            composable(route = Routes.Recommend.routes) {
                RecommendScreen(navController = navController)
            }
            composable(route = Routes.Filter.routes) {
                FilterScreen(navController = navController)
            }
            composable(route = Routes.Profile.routes) {
                ProfileScreen(navController = navController)
            }
            composable(route = Routes.Details.routes) {
                DetailsScreen(navController = navController)
            }
        }
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        isBottomBarVisible = when (destination.route) {
            Routes.Home.routes, Routes.Recommend.routes, Routes.Filter.routes, Routes.Profile.routes -> {
                true
            }

            else -> {
                false
            }
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview() {
    AppNavigation()
}
