package com.example.myalbum.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.ui.PhotoScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = "home",
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("home") {
            PhotoScreen(
                onNavigateEditScreen = {
                    navController.navigate("edit") {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable("edit") {
            EditScreen(
                onNavigatePhotoScreen = {
                    navController.navigate("home") {
                    }
                }
            )
        }
    }
}