package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myalbum.core.data.AppDrawerItem
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel
import java.lang.reflect.Modifier

@Composable
fun MainNavHost(
    modifier: Modifier,
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
                viewModel = topViewModel(),
                onFinishApp = onFinishApp,
                onNavigateToEditScreen = { photoId ->
                    navController.navigate("edit/photoId")
                },
                onNavigateToPreview = { photoId ->
                    navController.navigate("preview/photoId")
                },
            )
        }
        composable(
            "edit/{photoId}",
            arguments = listOf(
                navArgument("hotoId") { type = NavType. },
            ),
        ) {
            EditScreen(
                viewModel = hiltViewModel(),
                onNavigateUp = { navController.popBackStack("top", inclusive = false) },
            )
        }