package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.core.data.AppDrawerItem
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel

@Composable
fun MainNavHost(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    isExPandedScreen: Boolean,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit,
    startDestination: String = NavigationDestinations.TOP_ROUTE,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    )
    {
        composable(
            route = NavigationDestinations.TOP_ROUTE,
        ) { navBackStackEntry ->
        }
        composable(NavigationDestinations.EDIT_ROUTE) {
            EditScreen(
                navController = navController,
            )
        }
        composable("PreviewScreen") {
            PreviewScreen(
                navController = navController
            )
        }
    }
}