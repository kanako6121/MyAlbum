package com.example.myalbum.main

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object NavigationDestinations {
    const val TOP_ROUTE = "top"
    const val EDIT_ROUTE = "edit"
    const val PREVIEW_ROUTE = "preview"
}
class NavigationAction(navController: NavHostController) {
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
}