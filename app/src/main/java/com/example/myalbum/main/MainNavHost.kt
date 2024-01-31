package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.core.data.MainNavOption
import com.example.myalbum.core.data.PhotoRepository
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationDestinations.TOP_ROUTE,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("top") {
            TopScreen(
                viewModel = TopViewModel(repository = PhotoRepository()),
                onNavigationToEditScreen = { navController.navigate("edit") },
                onNavigationToPreviewScreen = { navController.navigate("preview") },
                menuItems = String(),
                drawerState = drawerState,
                defaultPick = MainNavOption.TopScreen
            )
        }
        composable("edit") {
            EditScreen(
                onNavigationToTopScreen = { navController.navigate("top") },
                onNavigationToPreviewScreen = { navController.navigate("preview") }
            )
        }
        composable("preview") {
            PreviewScreen(navController = navController)
        }
    }
}