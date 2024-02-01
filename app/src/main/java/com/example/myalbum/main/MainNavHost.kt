package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
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
        val navigateToTop: () -> Unit = {
            navController.navigate(NavigationDestinations.TOP_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
        val navigateToEdit: () -> Unit = {
            navController.navigate(NavigationDestinations.EDIT_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }

        val navigateToPreview: () -> Unit = {
            navController.navigate(NavigationDestinations.PREVIEW_ROUTE) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
        composable("top") {
            TopScreen(
                viewModel = TopViewModel(repository = PhotoRepository()),
                onNavigationToEditScreen = { navigateToEdit },
                onNavigationToPreviewScreen = { navigateToPreview },
                menuItems = String(),
                drawerState = drawerState,
                defaultPick = MainNavOption.TopScreen
            )
        }
        composable("edit") {
            EditScreen(
                onNavigationToTopScreen = { navigateToTop },
                onNavigationToPreviewScreen = { navigateToPreview }
            )
        }
        composable("preview") {
            PreviewScreen(
                onNavigationToTopScreen = { navigateToTop },
                onNavigationToEditScreen = { navigateToEdit }
            )
        }
    }
}

object NavigationDestinations {
    const val TOP_ROUTE = "top"
    const val EDIT_ROUTE = "edit"
    const val PREVIEW_ROUTE = "preview"
}