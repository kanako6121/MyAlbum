package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myalbum.core.data.MainNavOption
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen

@Composable
fun NavGraphBuilder.mainGraph(drawerState: DrawerState) {
    navigation(startDestination = MainNavOption.TopScreen, route = NavRoutes.MainRoute.name) {
        composable(MainNavOption.TopScreen.name) {
            TopScreen(drawerState)
        }
        composable(MainNavOption.EditScreen.name) {
            EditScreen(drawerState)
        }
        composable(MainNavOption.PreviewScreen.name) {
            PreviewScreen(drawerState)
        }
    }
}

enum class MainNavOption {
    TopScreen,
    EditScreen,
    PreviewScreen,
}