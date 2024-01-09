package com.example.myalbum.main

import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
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
    onFinishApp: () -> Unit,
    ) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        exitTransition = {
            slideOut { fullSize -> IntOffset(fullSize.width, 0) }
        },
        popExitTransition = {
            slideOut { fullSize -> IntOffset(fullSize.width, 0) }
        },
        enterTransition = {
            slideIn { fullSize -> IntOffset(fullSize.width, 0) }
        },
        popEnterTransition = {
            slideIn { fullSize -> IntOffset(-fullSize.width, 0) }
        }
    ) {
        composable("top") {
            TopScreen(
                onNavigateEditScreen = {
                    navController.navigate("edit") {
                    }
                },
                onNavigatePreviewScreen = {
                    navController.navigate("preview") {
                        launchSingleTop = true
                    }
                },
                onNavigateUp = {
                    navController.popBackStack("top", inclusive = false)
                               },
                onFinishApp = onFinishApp,
                viewModel = viewModel,
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
                },
                onNavigateUp = { navController.popBackStack("top", inclusive = false) },
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
                },
                onNavigateUp = { navController.popBackStack("top", inclusive = false) },
            )
        }
    }
}