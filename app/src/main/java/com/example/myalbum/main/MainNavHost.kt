package com.example.myalbum.main

import androidx.compose.animation.ExperimentalAnimationApi
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
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = "top",
) {
    val navController = rememberAnimatedNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "top",
        exitTransition = {
            slideOut { fullSize -> IntOffset(-fullSize.width, 0) }
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
                onNavigateUp = { navController.popBackStack("top", inclusive = false) },
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