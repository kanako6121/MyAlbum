package com.example.myalbum.main

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myalbum.core.data.PhotoRepository
import com.example.myalbum.feature.edit.EditScreen
import com.example.myalbum.feature.preview.PreviewScreen
import com.example.myalbum.feature.top.TopScreen
import com.example.myalbum.feature.top.TopViewModel

fun EditScreen(onNavigateUp: () -> Boolean) {

}

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
                onNavigateToEditScreen = { 
                    navController.navigate("edit/photoId")
                }, 
                composable("edit") {
            EditScreen(
                onNavigateUp = { navController.popBackStack("top", inclusive = false) },
            )
        }
        composable(
            "edit/{photoId},
                    arguments = listOf(
                    navArgument("photoId") { type =  },
        ),
        ) {
        PreviewScreen(
            viewModel = hiltViewModel(),
            onNavigateUp = { navController.popBackStack("top", inclusive = false) },
        )
    }
    }
}

enum class Screen(val route: String) {
    TOP("top"),
    EDIT("edit"),
    PREVIEW("preview"),
}