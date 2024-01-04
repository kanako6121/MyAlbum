package com.example.myalbum.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = "top",
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("top") {
            TopScreen(
                onNavigateEditScreen = {
                    navController.navigate("edit") {
                        launchSingleTop = true
                    }
                },
                onNavigatePreviewScreen = {
                    navController.navigate("preview") {
                        launchSingleTop = true
                    }
                },
            )
        }
        composable("edit") {
            EditScreen(
                onNavigateTopScreen = {
                    navController.navigate("top") {
                    }
                },
                onNavigatePreviewScreen = {
                    navController.navigate("preview") {
                    }
                }
            )
        }
        composable("preview") {
            PreviewScreen(
                onNavigateTopScreen = {
                    navController.navigate("top") {
                    }
                },
                onNavigateEditScreen = {
                    navController.navigate("edit") {
                    }
                }
            )
        }
    }
}